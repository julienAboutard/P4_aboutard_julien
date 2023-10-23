package com.example.maru.meetinglist;

public interface OnMeetingClickedListener {
    void onMeetingClicked(long neighbourId);

    void onMeetingAddClicked();

    void onDeleteMeetingClicked(long neighbourId);
}
