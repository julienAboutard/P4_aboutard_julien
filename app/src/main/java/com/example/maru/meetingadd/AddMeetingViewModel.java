package com.example.maru.meetingadd;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.util.SingleLiveEvent;

import java.time.LocalTime;

public class AddMeetingViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository meetingRepository;

    private final MutableLiveData<Boolean> isSaveButtonEnabledMutableLiveData = new MutableLiveData<>(false);

    private final SingleLiveEvent<Void> closeActivitySingleLiveEvent = new SingleLiveEvent<>();

    private Room selectedRoom;
    private LocalTime selectedTime = LocalTime.now();

    public AddMeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public LiveData<Boolean> getIsSaveButtonEnabledLiveData() {
        return isSaveButtonEnabledMutableLiveData;
    }

    public SingleLiveEvent<Void> getCloseActivitySingleLiveEvent() {
        return closeActivitySingleLiveEvent;
    }

    public void onAddButtonClicked(@NonNull String topic, @NonNull String mailList) {
        // Add neighbour to the repository...
        meetingRepository.addMeeting(selectedRoom, selectedTime, topic, mailList);
        // ... and close the Activity !
        closeActivitySingleLiveEvent.call();
    }

    public void onRoomSelected(@NonNull Room room) {
        selectedRoom = room;
    }

    public void onSelectedTime(@NonNull LocalTime selectedTime) {
        this.selectedTime = selectedTime;
    }

}
