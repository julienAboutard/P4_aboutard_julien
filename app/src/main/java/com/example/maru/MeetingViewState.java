package com.example.maru;

import com.example.maru.filter.HourFilterItemViewState;
import com.example.maru.meetinglist.MeetingsViewStateItem;

import java.util.List;
import java.util.Objects;

public class MeetingViewState {

    private final List<MeetingsViewStateItem> meetingsViewStateItems;

    private final List<HourFilterItemViewState> hourFilterItemViewStates;

    public MeetingViewState(List<MeetingsViewStateItem> meetingsViewStateItems, List<HourFilterItemViewState> hourFilterItemViewStates) {
        this.meetingsViewStateItems = meetingsViewStateItems;
        this.hourFilterItemViewStates = hourFilterItemViewStates;
    }

    public List<MeetingsViewStateItem> getMeetingsViewStateItems() {
        return meetingsViewStateItems;
    }

    public List<HourFilterItemViewState> getHourFilterItemViewStates() {
        return hourFilterItemViewStates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewState that = (MeetingViewState) o;
        return meetingsViewStateItems.equals(that.meetingsViewStateItems) && hourFilterItemViewStates.equals(that.hourFilterItemViewStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingsViewStateItems, hourFilterItemViewStates);
    }

    @Override
    public String toString() {
        return "MeetingViewState{" +
            "meetingsViewStateItems=" + meetingsViewStateItems +
            ", hourFilterItemViewStates=" + hourFilterItemViewStates +
            '}';
    }
}
