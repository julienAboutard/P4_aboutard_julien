package com.example.maru.meetinglist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;


    public MeetingsViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
        meetingRepository.generateRandomMeetings();
    }

    public void onDeleteMeetingClicked(long neighbourId) {
        meetingRepository.deleteNeighbour(neighbourId);
    }

    public LiveData<List<MeetingsViewStateItem>> getMeetingLiveData() {
        return Transformations.map(meetingRepository.getMeetingsLiveData(), meetings -> {
            List<MeetingsViewStateItem> Meetings = new ArrayList<>();
            return Meetings;
        });
    }
}
