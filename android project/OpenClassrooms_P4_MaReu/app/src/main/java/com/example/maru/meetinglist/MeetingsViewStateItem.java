package com.example.maru.meetinglist;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MeetingsViewStateItem {

    private final long id;
    @NonNull
    private final String topic;
    @NonNull
    private final String room;

    @NonNull
    private final String time;


    public MeetingsViewStateItem(long id, @NonNull String room, @NonNull String topic, @NonNull String time) {
        this.id = id;
        this.room = room;
        this.topic = topic;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTopic() {
        return topic;
    }

    @NonNull
    public String getRoom() {
        return room;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingsViewStateItem that = (MeetingsViewStateItem) o;
        return id == that.id && room.equals(that.room) && topic.equals(that.topic) && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, time, topic);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingsViewStateItem{" +
            "id=" + id +
            ", room='" + room + '\'' +
            ", time='" + time + '\'' +
            ", topic='" + topic + '\'' +
            '}';
    }
}
