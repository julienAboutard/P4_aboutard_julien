package com.example.maru.meetingdetail;

import androidx.annotation.NonNull;

import com.example.maru.data.Room;

import java.util.Objects;

public class MeetingDetailViewStateItem {
    @NonNull
    private final Room room;
    @NonNull
    private final String time;
    @NonNull
    private final String topic;
    @NonNull
    private final String mail_list;

    @NonNull
    public Room getRoom() {
        return room;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public String getMail_list() {
        return mail_list;
    }

    public MeetingDetailViewStateItem(
            @NonNull Room room,
            @NonNull String time,
            @NonNull String topic,
            @NonNull String mail_list
    ) {
        this.room = room;
        this.time = time;
        this.topic = topic;
        this.mail_list = mail_list;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingDetailViewStateItem that = (MeetingDetailViewStateItem) o;
        return room.equals(that.room) && time.equals(that.time) && topic.equals(that.topic) && mail_list.equals(that.mail_list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, time, topic, mail_list);
    }

    @Override
    public String toString() {
        return "MeetingDetailViewStateItem{" +
                "room='" + room + '\'' +
                ", time='" + time + '\'' +
                ", topic='" + topic + '\'' +
                ", mail_list='" + mail_list + '\'' +
                '}';
    }
}
