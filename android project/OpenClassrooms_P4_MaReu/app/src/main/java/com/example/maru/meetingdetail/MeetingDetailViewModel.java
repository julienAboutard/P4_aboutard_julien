package com.example.maru.meetingdetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;

public class MeetingDetailViewModel extends ViewModel {

    @NonNull
    private final MeetingRepository meetingRepository;

    public MeetingDetailViewModel(@NonNull Application application, @NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public LiveData<MeetingDetailViewStateItem> getMeetingDetailViewStateLiveData(long meetingId) {
        return Transformations.map(
                meetingRepository.getMeetingLiveData(meetingId),
                meeting -> new MeetingDetailViewStateItem(
                        meeting.getTopic(),
                        meeting.getRoom(),
                        meeting.getTime(),
                        meeting.getMail_list()
                )
        );
    }
}
