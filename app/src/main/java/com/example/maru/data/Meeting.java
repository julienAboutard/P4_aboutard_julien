package com.example.maru.data;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.Objects;

public class Meeting {

    private final long id;

    @NonNull
    private final LocalTime time;

    @NonNull
    private final Room room;

    @NonNull
    private final String topic;

    @NonNull
    private final String mail_list;

    public Meeting(long id, @NonNull Room room, @NonNull LocalTime time, @NonNull String topic, @NonNull String mail_list) {
        this.id = id;
        this.room = room;
        this.time = time;
        this.topic = topic;
        this.mail_list = mail_list;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public LocalTime getTime() {
        return time;
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public String getMailList() {
        return mail_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && room.equals(meeting.room) && time.equals(meeting.time) && topic.equals(meeting.topic) && mail_list.equals(meeting.mail_list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, time, topic, mail_list);
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting{" +
            "id=" + id +
            ", room='" + room + '\'' +
            ", time='" + time + '\'' +
            ", topic='" + topic + '\'' +
            ", mail_list='" + mail_list + '\'' +
            '}';
    }
}
