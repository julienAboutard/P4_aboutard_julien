package com.example.maru.meetings;

import androidx.annotation.NonNull;

import com.example.maru.filter.hour.HourFilterViewStateItem;
import com.example.maru.filter.room.RoomFilterViewStateItem;

import java.util.List;
import java.util.Objects;

public class MeetingsViewState {

    @NonNull
    private final List<MeetingsViewStateItem> meetingsViewStateItems;

    @NonNull
    private final List<HourFilterViewStateItem> hourFilterViewStateItems;

    @NonNull
    private final List<RoomFilterViewStateItem> roomFilterViewStateItems;

    public MeetingsViewState(
        @NonNull List<MeetingsViewStateItem> meetingsViewStateItems,
        @NonNull List<HourFilterViewStateItem> hourFilterViewStateItems,
        @NonNull List<RoomFilterViewStateItem> roomFilterViewStateItems) {
        this.meetingsViewStateItems = meetingsViewStateItems;
        this.hourFilterViewStateItems = hourFilterViewStateItems;
        this.roomFilterViewStateItems = roomFilterViewStateItems;
    }

    @NonNull
    public List<MeetingsViewStateItem> getMeetingsViewStateItems() {
        return meetingsViewStateItems;
    }

    @NonNull
    public List<HourFilterViewStateItem> getHourFilterViewStateItems() {
        return hourFilterViewStateItems;
    }

    @NonNull
    public List<RoomFilterViewStateItem> getRoomFilterViewStateItems() {
        return roomFilterViewStateItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingsViewState that = (MeetingsViewState) o;
        return Objects.equals(meetingsViewStateItems, that.meetingsViewStateItems) && Objects.equals(hourFilterViewStateItems, that.hourFilterViewStateItems) && Objects.equals(roomFilterViewStateItems, that.roomFilterViewStateItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingsViewStateItems, hourFilterViewStateItems, roomFilterViewStateItems);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingsViewState{" +
            "meetingsViewStateItems=" + meetingsViewStateItems +
            ", hourFilterViewStateItems=" + hourFilterViewStateItems +
            ", roomFilterViewStateItems=" + roomFilterViewStateItems +
            '}';
    }

}
