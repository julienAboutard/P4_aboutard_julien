package com.example.maru.filter.room;

import androidx.annotation.NonNull;

import com.example.maru.data.Room;

import java.util.Objects;

public class RoomFilterViewStateItem {

    @NonNull
    private final Room room;

    private final boolean selectedStatus;

    public RoomFilterViewStateItem(@NonNull Room room, boolean selectedStatus) {
        this.room = room;
        this.selectedStatus = selectedStatus;
    }

    @NonNull
    public Room getRoom() {
        return room;
    }

    public boolean isSelectedStatus() {
        return selectedStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomFilterViewStateItem that = (RoomFilterViewStateItem) o;
        return selectedStatus == that.selectedStatus && room == that.room;
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, selectedStatus);
    }

    @NonNull
    @Override
    public String toString() {
        return "RoomFilterViewStateItem{" +
            "room=" + room +
            ", selectedStatus=" + selectedStatus +
            '}';
    }
}
