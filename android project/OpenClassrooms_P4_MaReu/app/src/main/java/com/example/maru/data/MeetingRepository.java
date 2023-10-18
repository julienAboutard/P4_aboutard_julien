package com.example.maru.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;



    public void addMeeting(
            @NonNull String room,
            @NonNull String time,
            @Nullable String topic,
            @Nullable List<String> mail_list
    ) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) return;

        meetings.add(
                new Meeting(
                        maxId++,
                        room,
                        time,
                        topic,
                        mail_list
                )
        );

        meetingLiveData.setValue(meetings);
    }

    public void deleteNeighbour(long neighbourId) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) return;

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == neighbourId) {
                iterator.remove();
                break;
            }
        }

        meetingLiveData.setValue(meetings);
    }


    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingLiveData;
    }

}
