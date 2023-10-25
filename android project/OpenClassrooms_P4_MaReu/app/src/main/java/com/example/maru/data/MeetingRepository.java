package com.example.maru.data;

import static com.example.maru.data.Room.Lucina;
import static com.example.maru.data.Room.ROY;
import static com.example.maru.data.Room.Robin;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public void addMeeting(
        @NonNull Room room,
        @NonNull String time,
        @NonNull String topic,
        @NonNull String mail_list
    ) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) {
            meetings = new ArrayList<>();
        }

        meetings.add(
            new Meeting(
                maxId,
                room,
                time,
                topic,
                mail_list
            )
        );
        maxId++;
        meetingLiveData.setValue(meetings);
    }

    public void deleteNeighbour(long meetingId) {
        List<Meeting> meetings = meetingLiveData.getValue();

        if (meetings == null) {
            meetings = new ArrayList<>();
        }

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }

        meetingLiveData.setValue(meetings);
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingLiveData;
    }

    public LiveData<Meeting> getMeetingLiveData(long meetingId) {

        return Transformations.map(meetingLiveData, meetings -> {
            for (Meeting meeting : meetings) {
                if (meeting.getId() == meetingId) {
                    return meeting;
                }
            }

            return null;
        });
    }

    public void generateRandomMeetings() {
        addMeeting(
            ROY,
            "8:00",
            "Asura",
            "adam@test.fr, eve@test.fr, zeus@test.fr, athena@test.fr"
        );
        addMeeting(
            Lucina,
            "16:00",
            "Aphrodite",
            "thor@test.fr, jord@test.fr, vishnu@test.fr, shiva@test.fr"
        );
        addMeeting(
            Robin,
            "14:00",
            "Astarté",
            "brahma@test.fr, thot@test.fr, ra@test.fr"
        );
        addMeeting(
            ROY,
            "10:00",
            "Enfer",
            "adam@test.fr, eve@test.fr, zeus@test.fr, athena@test.fr"
        );
    }

}
