package com.example.maru.meetingadd;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.util.SingleLiveEvent;

import java.time.LocalTime;

public class AddMeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository meetingRepository;

    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();

    private Room selectedRoom;
    private LocalTime selectedTime = LocalTime.now();

    public AddMeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    public void onAddButtonClicked(@NonNull String topic, @NonNull String mailList) {
        meetingRepository.addMeeting(selectedRoom, selectedTime, topic, mailList);
        closeActivitySingleLiveEvent.call();
    }

    public void onRoomSelected(@NonNull Room room) {
        selectedRoom = room;
    }

    public void onSelectedTime(@NonNull LocalTime selectedTime) {
        this.selectedTime = selectedTime;
    }

}
