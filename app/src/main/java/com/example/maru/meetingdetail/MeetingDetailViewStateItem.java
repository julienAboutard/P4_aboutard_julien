package com.example.maru.meetingdetail;

import androidx.annotation.NonNull;

import com.example.maru.data.Room;

import java.time.LocalTime;
import java.util.Objects;

public class MeetingDetailViewStateItem {
    @NonNull
    private final Room room;
    @NonNull
    private final LocalTime time;
    @NonNull
    private final String topic;
    @NonNull
    private final String mailList;

    @NonNull
    public Room getRoom() {
        return room;
    }

    @NonNull
    public LocalTime getTime() {
        return time;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public String getMailList() {
        return mailList;
    }

    public MeetingDetailViewStateItem(
        @NonNull Room room,
        @NonNull LocalTime time,
        @NonNull String topic,
        @NonNull String mailList
    ) {
        this.room = room;
        this.time = time;
        this.topic = topic;
        this.mailList = mailList;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingDetailViewStateItem that = (MeetingDetailViewStateItem) o;
        return room.equals(that.room) && time.equals(that.time) && topic.equals(that.topic) && mailList.equals(that.mailList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, time, topic, mailList);
    }

    @Override
    public String toString() {
        return "MeetingDetailViewStateItem{" +
            "room='" + room + '\'' +
            ", time='" + time + '\'' +
            ", topic='" + topic + '\'' +
            ", mail_list='" + mailList + '\'' +
            '}';
    }
}
