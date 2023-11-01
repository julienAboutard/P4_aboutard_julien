package com.example.maru;

import com.example.maru.filter.hour.HourFilterViewStateItem;
import com.example.maru.meetinglist.MeetingsViewStateItem;

import java.util.List;
import java.util.Objects;

public class MeetingViewState {

    private final List<MeetingsViewStateItem> meetingsViewStateItems;

    private final List<HourFilterViewStateItem> hourFilterViewStateItems;

    public MeetingViewState(List<MeetingsViewStateItem> meetingsViewStateItems, List<HourFilterViewStateItem> hourFilterViewStateItems) {
        this.meetingsViewStateItems = meetingsViewStateItems;
        this.hourFilterViewStateItems = hourFilterViewStateItems;
    }

    public List<MeetingsViewStateItem> getMeetingsViewStateItems() {
        return meetingsViewStateItems;
    }

    public List<HourFilterViewStateItem> getHourFilterItemViewStates() {
        return hourFilterViewStateItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewState that = (MeetingViewState) o;
        return meetingsViewStateItems.equals(that.meetingsViewStateItems) && hourFilterViewStateItems.equals(that.hourFilterViewStateItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingsViewStateItems, hourFilterViewStateItems);
    }

    @Override
    public String toString() {
        return "MeetingViewState{" +
            "meetingsViewStateItems=" + meetingsViewStateItems +
            ", hourFilterItemViewStates=" + hourFilterViewStateItems +
            '}';
    }
}
