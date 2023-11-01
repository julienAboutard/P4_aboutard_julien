package com.example.maru;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.filter.hour.HourFilterViewStateItem;
import com.example.maru.meetinglist.MeetingsViewStateItem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;

    private final MediatorLiveData<MeetingViewState> meetingViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Map<LocalTime, Boolean>> hoursLiveData = new MutableLiveData<>();

    private final MutableLiveData<Map<Room, Boolean>> roomsLiveData = new MutableLiveData<>();

    /**
     * Construct a new ViewModel instance.
     * <p>
     * You should <strong>never</strong> manually construct a ViewModel outside of a
     * {@link ViewModelProvider.Factory}.
     */
    public MeetingsViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;

        meetingMediatorLiveData();
    }

    public void onDeleteMeetingClicked(long neighbourId) {
        meetingRepository.deleteNeighbour(neighbourId);
    }


    public void meetingMediatorLiveData() {
        final LiveData<List<Meeting>> meetingsLiveData = meetingRepository.getMeetingsLiveData();
        setHoursLiveData(hoursDistribution());

        meetingViewStateMediatorLiveData.addSource(meetingsLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                meetingViewStateMediatorLiveData.setValue(
                    combine(meetings, hoursLiveData.getValue(), roomsLiveData.getValue())
                );
            }
        });

        meetingViewStateMediatorLiveData.addSource(hoursLiveData, new Observer<Map<LocalTime, Boolean>>() {
            @Override
            public void onChanged(Map<LocalTime, Boolean> localTimeBooleanMap) {
                meetingViewStateMediatorLiveData.setValue(
                    combine(meetingsLiveData.getValue(), localTimeBooleanMap, roomsLiveData.getValue())
                );
            }
        });

        meetingViewStateMediatorLiveData.addSource(roomsLiveData, new Observer<Map<Room, Boolean>>() {
            @Override
            public void onChanged(Map<Room, Boolean> roomBooleanMap) {
                meetingViewStateMediatorLiveData.setValue((
                    combine(meetingsLiveData.getValue(), hoursLiveData.getValue(), roomBooleanMap )
                    ));
            }
        });
    }

    public void setHoursLiveData(Map<LocalTime, Boolean> localTimeBooleanMap){
        hoursLiveData.setValue(localTimeBooleanMap);
    }

    public void setRoomsLiveData(Map<Room, Boolean> roomBooleanMap) {
        roomsLiveData.setValue(roomBooleanMap);
    }

    public MediatorLiveData<MeetingViewState> getListFilterMeetings(){
        return meetingViewStateMediatorLiveData;
    }

    private MeetingViewState combine(
        @NonNull List<Meeting> meetings,
        @NonNull Map<LocalTime, Boolean> localTimeBooleanMap,
        @NonNull Map<Room, Boolean> roomBooleanMap){
        List<Meeting> filteredMeetings = filterMeetings(meetings, localTimeBooleanMap, roomBooleanMap);
        List<MeetingsViewStateItem> meetingsViewStateItems = new ArrayList<>();
        for (Meeting meeting : filteredMeetings) {
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

        List<HourFilterViewStateItem> hourFilterViewStateItems = getHourFilterItemStateView(localTimeBooleanMap);

        return new MeetingViewState(
            meetingsViewStateItems,
            hourFilterViewStateItems
        );
    }
    private Map<LocalTime, Boolean> hoursDistribution() {
        Map<LocalTime, Boolean> hours = new LinkedHashMap<>();
        for (int hour = 6; hour <= 22; hour++) {
            hours.put(LocalTime.of(hour, 0), false);
        }
        return hours;
    }

    private Map<Room, Boolean> roomsDistribution() {
        Map<Room, Boolean> rooms = new LinkedHashMap<>();
        for (Room room : Room.values()) {
            rooms.put(room, false;
        }
        return rooms;
    }
    private List<Meeting> filterMeetings(
        @NonNull List<Meeting> meetings,
        @NonNull Map<LocalTime, Boolean> localTimeBooleanMap
    ) {
        List<Meeting> filteredMeetings = new ArrayList<>();
        boolean hourSelected = false;
        boolean hourMatchMeeting = false;

        if (meetings == null) {return filteredMeetings;}

        for (Meeting meeting : meetings) {
            for (Map.Entry<LocalTime, Boolean> mapHourEntry : localTimeBooleanMap.entrySet()){
                LocalTime hour = mapHourEntry.getKey();
                boolean selectedStatus = mapHourEntry.getValue();

                if (selectedStatus) {hourSelected= true;}

                if (meeting.getTime().equals(hour)) {
                    hourMatchMeeting = selectedStatus;
                }
            }
            if (!hourSelected) {hourMatchMeeting = true;}
            if (hourMatchMeeting) {filteredMeetings.add(meeting);}
        }
        return filteredMeetings;
    }

    private List<HourFilterViewStateItem> getHourFilterItemStateView(@NonNull Map<LocalTime, Boolean> localTimeBooleanMap) {
        List<HourFilterViewStateItem> hourFilterViewStateItems = new ArrayList<>();

        for (Map.Entry<LocalTime, Boolean> hours : localTimeBooleanMap.entrySet()) {
            LocalTime time = hours.getKey();
            boolean selectedStatus = hours.getValue();

            @ColorRes
            int colorText;

            if (selectedStatus) {
                colorText = android.R.color.black;
            } else {
                colorText = android.R.color.white;
            }

            hourFilterViewStateItems.add(
                new HourFilterViewStateItem(
                    time,
                    colorText
                )
            );
        }

        return hourFilterViewStateItems;
    }

    public void onHourSelected(@NonNull LocalTime hour) {
        Map<LocalTime, Boolean> hours = hoursLiveData.getValue();

        if (hours == null) {
            return;
        }

        for (Map.Entry<LocalTime, Boolean> hoursEntry : hours.entrySet()) {
            if (hoursEntry.getKey().equals(hour)) {
                hoursEntry.setValue(!hoursEntry.getValue());
                break;
            }
        }
        hoursLiveData.setValue(hours);
    }
}
