package com.example.maru.meetinglist;

public interface OnMeetingClickedListener {
    void onMeetingClicked(long neighbourId);

    void onDeleteMeetingClicked(long neighbourId);
}
