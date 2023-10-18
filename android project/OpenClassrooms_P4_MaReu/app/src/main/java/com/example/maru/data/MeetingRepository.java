package com.example.maru.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public MeetingRepository() {
        generateRandomMeetings();
    }

    public void addMeeting(
            @NonNull String room,
            @NonNull String time,
            @NonNull String topic,
            @NonNull String mail_list
    ) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) meetings = new ArrayList<Meeting>();

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

    private void generateRandomMeetings() {
        addMeeting(
                "802",
                "8:00",
                "Asura",
                "adam@test.fr, eve@test.fr, zeus@test.fr, athena@test.fr"
        );
        addMeeting(
                "389",
                "16:00",
                "Aphrodite",
                "thor@test.fr, jord@test.fr, vishnu@test.fr, shiva@test.fr"
        );
        addMeeting(
                "103",
                "14:00",
                "Astarté",
                "brahma@test.fr, thot@test.fr, ra@test.fr"
        );
    }

}
