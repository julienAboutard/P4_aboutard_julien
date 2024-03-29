package com.example.maru.meetings;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.filter.hour.HourFilterViewStateItem;
import com.example.maru.filter.room.RoomFilterViewStateItem;

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
        roomsLiveData.setValue(getRoomsDistribution());

        meetingViewStateMediatorLiveData.addSource(
            meetingsLiveData, meetings -> combine(meetings, hoursLiveData.getValue(), roomsLiveData.getValue())
        );

        meetingViewStateMediatorLiveData.addSource(
            hoursLiveData, localTimeBooleanMap -> combine(meetingsLiveData.getValue(), localTimeBooleanMap, roomsLiveData.getValue())
        );

        meetingViewStateMediatorLiveData.addSource(
            roomsLiveData, roomBooleanMap -> combine(meetingsLiveData.getValue(), hoursLiveData.getValue(), roomBooleanMap)
        );
    }

    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }

    public MediatorLiveData<MeetingsViewState> getListFilterMeetings() {
        return meetingViewStateMediatorLiveData;
    }

    /**
     * Combine Live Data and filter meeting with these parameters
     * @param meetings list of meetings in database
     * @param localTimeBooleanMap map of LocalTime object and boolean
     * @param roomBooleanMap map of Room object and boolean
     */
    private void combine(
        @Nullable List<Meeting> meetings,
        @Nullable Map<LocalTime, Boolean> localTimeBooleanMap,
        @Nullable Map<Room, Boolean> roomBooleanMap) {
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
                    meeting.getMailList()));
        }

        List<HourFilterViewStateItem> hourFilterViewStateItems = getHourFilterItemStateView(localTimeBooleanMap);
        List<RoomFilterViewStateItem> roomFilterViewStateItems = getRoomFilterItemStateView(roomBooleanMap);

        meetingViewStateMediatorLiveData.setValue(
            new MeetingsViewState(
                meetingsViewStateItems,
                hourFilterViewStateItems,
                roomFilterViewStateItems));

    }

    /**
     * create the distribution of the hours for the filter
     * Step of 2 to not overload the filter
     * @return a Map of :
     * LocalTime object for hours filter to select as key
     * boolean to selected status as values
     */
    private Map<LocalTime, Boolean> getHoursDistribution() {
        Map<LocalTime, Boolean> hours = new LinkedHashMap<>();
        for (int hour = 6; hour <= 22; hour += 2) {
            hours.put(LocalTime.of(hour, 0), false);
        }
        return hours;
    }

    /**
     * create the distribution of the rooms for the filter
     * @return a Map of :
     * Room object for rooms filter to select as key
     * boolean to selected status as values
     */
    private Map<Room, Boolean> getRoomsDistribution() {
        Map<Room, Boolean> rooms = new LinkedHashMap<>();
        for (Room room : Room.values()) {
            rooms.put(room, false);
        }
        return rooms;
    }

    /**
     * Filter the Meetings by hour or/and room selected
     * @param meetings list of meetings in database
     * @param localTimeBooleanMap map of LocalTime object and boolean
     * @param roomBooleanMap map of Room object and boolean
     * @return a List of Meeting Object filtered or not by the filter selected
     */
    private List<Meeting> filterMeetings(
        @NonNull List<Meeting> meetings,
        @Nullable Map<LocalTime, Boolean> localTimeBooleanMap,
        @Nullable Map<Room, Boolean> roomBooleanMap) {
        if (localTimeBooleanMap == null || roomBooleanMap == null) {
            return meetings;
        }

        List<Meeting> filteredMeetings = new ArrayList<>();
        boolean oneHourSelected = false;
        boolean oneRoomSelected = false;

        for (Meeting meeting : meetings) {

            boolean hourMatchMeeting = false;
            boolean roomMatchMeeting = false;

            for (Map.Entry<Room, Boolean> mapRoomEntry : roomBooleanMap.entrySet()) {
                Room room = mapRoomEntry.getKey();
                boolean roomSelectedStatus = mapRoomEntry.getValue();

                if (roomSelectedStatus) {
                    oneRoomSelected = true;
                }

                if (roomSelectedStatus && meeting.getRoom().equals(room)) {
                    roomMatchMeeting = true;
                }
            }

            for (Map.Entry<LocalTime, Boolean> mapHourEntry : localTimeBooleanMap.entrySet()) {
                LocalTime hour = mapHourEntry.getKey();
                boolean hourSelectedStatus = mapHourEntry.getValue();

                if (hourSelectedStatus) {
                    oneHourSelected = true;
                }

                if (hourSelectedStatus && (
                    meeting.getTime().getHour() == hour.getHour() ||
                        meeting.getTime().getHour() == hour.plusHours(2).minusMinutes(1).getHour() ||
                        meeting.getTime().isAfter(hour) && meeting.getTime().isBefore(hour.plusHours(2).minusMinutes(1))
                )) {
                    hourMatchMeeting = true;
                }
            }
            if (!oneHourSelected) {
                hourMatchMeeting = true;
            }
            if (!oneRoomSelected) {
                roomMatchMeeting = true;
            }
            if (hourMatchMeeting && roomMatchMeeting) {
                filteredMeetings.add(meeting);
            }
        }
        return filteredMeetings;
    }

    private List<HourFilterViewStateItem> getHourFilterItemStateView(@Nullable Map<LocalTime, Boolean> localTimeBooleanMap) {
        List<HourFilterViewStateItem> hourFilterViewStateItems = new ArrayList<>();

        if (localTimeBooleanMap == null) {
            throw new IllegalStateException("localTimeBooleanMap must be initialized !");
        }
        for (Map.Entry<LocalTime, Boolean> hours : localTimeBooleanMap.entrySet()) {
            LocalTime time = hours.getKey();
            boolean selectedStatus = hours.getValue();

            @ColorRes int colorText;

            if (selectedStatus) {
                colorText = android.R.color.black;
            } else {
                colorText = android.R.color.white;
            }

            hourFilterViewStateItems.add(new HourFilterViewStateItem(time, colorText));
        }

        return hourFilterViewStateItems;
    }

    private List<RoomFilterViewStateItem> getRoomFilterItemStateView(@Nullable Map<Room, Boolean> roomBooleanMap) {
        List<RoomFilterViewStateItem> roomFilterViewStateItems = new ArrayList<>();

        if (roomBooleanMap == null) {
            throw new IllegalStateException("roomBooleanMap must be initialized !");
        }

        for (Map.Entry<Room, Boolean> mapRoomEntry : roomBooleanMap.entrySet()) {
            Room room = mapRoomEntry.getKey();
            boolean selectedStatus = mapRoomEntry.getValue();

            roomFilterViewStateItems.add(new RoomFilterViewStateItem(room, selectedStatus));
        }

        return roomFilterViewStateItems;
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

    public void onRoomSelected(@NonNull Room room) {
        Map<Room, Boolean> rooms = roomsLiveData.getValue();

        if (rooms == null) {
            return;
        }

        for (Map.Entry<Room, Boolean> mapRoomEntry : rooms.entrySet()) {
            if (mapRoomEntry.getKey().equals(room)) {
                mapRoomEntry.setValue((!mapRoomEntry.getValue()));
                break;
            }
        }
        roomsLiveData.setValue(rooms);
    }

}
