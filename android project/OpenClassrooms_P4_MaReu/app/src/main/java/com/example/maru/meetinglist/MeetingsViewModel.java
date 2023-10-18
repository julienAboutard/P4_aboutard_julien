package com.example.maru.meetinglist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.MeetingRepository;

public class MeetingsViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;

    public MeetingsViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void onDeleteMeetingClicked(long neighbourId) {
        meetingRepository.deleteNeighbour(neighbourId);
    }
}
