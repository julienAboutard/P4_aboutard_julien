package com.example.maru;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.filter.HourFilterItemViewState;
import com.example.maru.meetinglist.MeetingsViewStateItem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;

    private final MediatorLiveData<MeetingViewState> meetingViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Map<LocalTime, Boolean>> selectedHoursLiveData = new MutableLiveData<>();

    /**
     * Construct a new ViewModel instance.
     * <p>
     * You should <strong>never</strong> manually construct a ViewModel outside of a
     * {@link ViewModelProvider.Factory}.
     */
    public MeetingsViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void onDeleteMeetingClicked(long neighbourId) {
        meetingRepository.deleteNeighbour(neighbourId);
    }


    public void meetingMediatorLiveData() {
        final LiveData<List<Meeting>> meetingsLiveData = meetingRepository.getMeetingsLiveData();

        meetingViewStateMediatorLiveData.addSource(meetingsLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                combine(meetings, selectedHoursLiveData.getValue());
            }
        });

        meetingViewStateMediatorLiveData.addSource(selectedHoursLiveData, new Observer<Map<LocalTime, Boolean>>() {
            @Override
            public void onChanged(Map<LocalTime, Boolean> localTimeBooleanMap) {
                combine(meetingsLiveData.getValue(), localTimeBooleanMap);
            }
        });
    }

    public void setSelectedHoursLiveData(Map<LocalTime, Boolean> localTimeBooleanMap){
        selectedHoursLiveData.setValue(localTimeBooleanMap);
    }

    public MediatorLiveData<MeetingViewState> getListFilterMeetings(){
        return meetingViewStateMediatorLiveData;
    }
    @NonNull
    private MeetingViewState sortAndFilterMeetings(
        @Nullable final List<Meeting> meetings,
        @Nullable final Map<Room, Boolean> selectedRooms,
        @Nullable final Map<LocalTime, Boolean> selectedHours
    ) {
        if (selectedRooms == null
            || selectedHours == null) {
            throw new IllegalStateException("All internal LiveData must be initialized !");
        }

        // Filter meetings...
        List<Meeting> filteredMeetings = getFilteredMeetings(meetings, selectedRooms, selectedHours);

        // ... and finally, map them !
        List<MeetingsViewStateItem> meetingViewStateItems = new ArrayList<>();
        for (Meeting filteredMeeting : filteredMeetings) {
            meetingViewStateItems.add(mapMeeting(filteredMeeting));
        }

        // Compute room filters state
        //List<MeetingViewStateRoomFilterItem> meetingViewStateRoomFilterItems = getMeetingViewStateRoomFilterItems(selectedRooms);

        // Compute hour filters state
        List<HourFilterItemViewState> meetingViewStateHourFilterItems = getMeetingViewStateHourFilterItems(selectedHours);

        // Expose this to the Activity !
        return new MeetingViewState(
            meetingViewStateItems,
            meetingViewStateHourFilterItems
        );
    }

    @NonNull
    private List<Meeting> getFilteredMeetings(@Nullable List<Meeting> meetings, @NonNull Map<Room, Boolean> selectedRooms, @NonNull Map<LocalTime, Boolean> selectedHours) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        if (meetings == null) {
            return filteredMeetings;
        }

        for (Meeting meeting : meetings) {

            boolean atLeastOneRoomIsSelected = false;
            boolean meetingRoomMatches = false;
            boolean atLeastOneHourIsSelected = false;
            boolean meetingHourMatches = false;

            for (Map.Entry<Room, Boolean> roomEntry : selectedRooms.entrySet()) {
                Room room = roomEntry.getKey();
                boolean isRoomSelected = roomEntry.getValue();

                if (isRoomSelected) {
                    atLeastOneRoomIsSelected = true;
                }

                if (room == meeting.getRoom()) {
                    meetingRoomMatches = isRoomSelected;
                }
            }

            for (Map.Entry<LocalTime, Boolean> hourEntry : selectedHours.entrySet()) {
                LocalTime time = hourEntry.getKey();
                boolean isTimeSelected = hourEntry.getValue();

                if (isTimeSelected) {
                    atLeastOneHourIsSelected = true;
                }

                if (meeting.getTime().equals(time)
                    || (meeting.getTime().isAfter(time) && meeting.getTime().isBefore(time.plusHours(1)))) {
                    meetingHourMatches = isTimeSelected;
                }
            }

            if (!atLeastOneRoomIsSelected) {
                meetingRoomMatches = true;
            }

            if (!atLeastOneHourIsSelected) {
                meetingHourMatches = true;
            }

            if (meetingRoomMatches && meetingHourMatches) {
                filteredMeetings.add(meeting);
            }
        }

        return filteredMeetings;
    }

    public LiveData<List<MeetingsViewStateItem>> getMeetingLiveData() {
        return Transformations.map(meetingRepository.getMeetingsLiveData(), meetings -> {
            List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>();

            for (Meeting meeting : meetings) {
                meetingsViewStateItems.add(
                    new MeetingsViewStateItem(
                        meeting.getId(),
                        meeting.getRoom(),
                        meeting.getTopic(),
                        meeting.getTime(),
                        meeting.getMailList()
                    )
                );
            }
            return meetingsViewStateItems;
        });
    }

    @NonNull
    private MeetingsViewStateItem mapMeeting(@NonNull Meeting meeting) {
        return new MeetingsViewStateItem(
            meeting.getId(),
            meeting.getRoom().getIcon(),
            resources.getString(
                R.string.meeting_title,
                meeting.getTopic(),
                DateTimeFormatter.ofPattern("HH:mm").format(meeting.getTime()),
                resources.getString(meeting.getRoom().getStringResName())
            ),
            stringifyParticipants(meeting.getParticipants())
        );
    }
}
