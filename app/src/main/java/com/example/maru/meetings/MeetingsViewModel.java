package com.example.maru.meetings;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.filter.hour.HourFilterViewStateItem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;

    private final MediatorLiveData<MeetingsViewState> meetingViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Map<LocalTime, Boolean>> hoursLiveData = new MutableLiveData<>();

    private final MutableLiveData<Map<Room, Boolean>> roomsLiveData = new MutableLiveData<>();

    /**
     * Construct a new ViewModel instance.
     * <p>
     * You should <strong>never</strong> manually construct a ViewModel outside of a
     * {@link com.example.maru.ViewModelFactory}.
     */
    public MeetingsViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;

        final LiveData<List<Meeting>> meetingsLiveData = meetingRepository.getMeetingsLiveData();
        hoursLiveData.setValue(getHoursDistribution());

        meetingViewStateMediatorLiveData.addSource(meetingsLiveData, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                combine(meetings, hoursLiveData.getValue(), roomsLiveData.getValue());
            }
        });

        meetingViewStateMediatorLiveData.addSource(hoursLiveData, new Observer<Map<LocalTime, Boolean>>() {
            @Override
            public void onChanged(Map<LocalTime, Boolean> localTimeBooleanMap) {
                combine(meetingsLiveData.getValue(), localTimeBooleanMap, roomsLiveData.getValue());
            }
        });

        meetingViewStateMediatorLiveData.addSource(roomsLiveData, new Observer<Map<Room, Boolean>>() {
            @Override
            public void onChanged(Map<Room, Boolean> roomBooleanMap) {
                combine(meetingsLiveData.getValue(), hoursLiveData.getValue(), roomBooleanMap);
            }
        });
    }

    public void onDeleteMeetingClicked(long neighbourId) {
        meetingRepository.deleteNeighbour(neighbourId);
    }

    public void setRoomsLiveData(Map<Room, Boolean> roomBooleanMap) {
        roomsLiveData.setValue(roomBooleanMap);
    }

    public MediatorLiveData<MeetingsViewState> getListFilterMeetings() {
        return meetingViewStateMediatorLiveData;
    }

    private void combine(
        @Nullable List<Meeting> meetings,
        @Nullable Map<LocalTime, Boolean> localTimeBooleanMap,
        @Nullable Map<Room, Boolean> roomBooleanMap
    ) {
        if (meetings == null) {
            return;
        }

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

        meetingViewStateMediatorLiveData.setValue(
            new MeetingsViewState(
                meetingsViewStateItems,
                hourFilterViewStateItems
            )
        );
    }

    private Map<LocalTime, Boolean> getHoursDistribution() {
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
        @Nullable Map<LocalTime, Boolean> localTimeBooleanMap
    ) {
        if (localTimeBooleanMap == null) {
            return meetings;
        }

        List<Meeting> filteredMeetings = new ArrayList<>();
        boolean hourSelected = false;
        boolean hourMatchMeeting = false;

        for (Meeting meeting : meetings) {
            for (Map.Entry<LocalTime, Boolean> mapHourEntry : localTimeBooleanMap.entrySet()) {
                LocalTime hour = mapHourEntry.getKey();
                boolean selectedStatus = mapHourEntry.getValue();

                if (selectedStatus) {
                    hourSelected = true;
                }

                if (meeting.getTime().equals(hour)) {
                    hourMatchMeeting = selectedStatus;
                }
            }
            if (!hourSelected) {
                hourMatchMeeting = true;
            }
            if (hourMatchMeeting) {
                filteredMeetings.add(meeting);
            }
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
