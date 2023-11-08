package com.example.maru.meetingadd.spinner;

import com.example.maru.data.Room;

public class SpinnerItem {

    private final Room room;

    public SpinnerItem(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
