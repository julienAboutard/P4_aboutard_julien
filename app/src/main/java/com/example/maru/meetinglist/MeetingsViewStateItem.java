package com.example.maru.meetinglist;

import androidx.annotation.NonNull;

import com.example.maru.data.Room;

import java.util.Objects;

public class MeetingsViewStateItem {

    private final long id;
    @NonNull
    private final String topic;
    @NonNull
    private final Room room;

    @NonNull
    private final String time;

    @NonNull
    private  final String mail_list;


    public MeetingsViewStateItem(long id, @NonNull Room room, @NonNull String topic, @NonNull String time, @NonNull String mailList) {
        this.id = id;
        this.room = room;
        this.topic = topic;
        this.time = time;
        mail_list = mailList;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @NonNull
    public String getMail_list() {
        return mail_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingsViewStateItem that = (MeetingsViewStateItem) o;
        return id == that.id && room.equals(that.room) && topic.equals(that.topic) && time.equals(that.time) && mail_list.equals(that.mail_list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, time, topic, mail_list);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingsViewStateItem{" +
            "id=" + id +
            ", room='" + room + '\'' +
            ", time='" + time + '\'' +
            ", topic='" + topic + '\'' +
            ", mail_list='" + mail_list + '\'' +
            '}';
    }
}
