package com.example.maru;

import com.example.maru.filter.HourFilterItemStateView;
import com.example.maru.meetinglist.MeetingsViewStateItem;

import java.util.List;
import java.util.Objects;

public class MeetingViewState {

    private final List<MeetingsViewStateItem> meetingsViewStateItems;

    private final List<HourFilterItemStateView> hourFilterItemStateViews;

    public MeetingViewState(List<MeetingsViewStateItem> meetingsViewStateItems, List<HourFilterItemStateView> hourFilterItemStateViews) {
        this.meetingsViewStateItems = meetingsViewStateItems;
        this.hourFilterItemStateViews = hourFilterItemStateViews;
    }

    public List<MeetingsViewStateItem> getMeetingsViewStateItems() {
        return meetingsViewStateItems;
    }

    public List<HourFilterItemStateView> getHourFilterItemViewStates() {
        return hourFilterItemStateViews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingViewState that = (MeetingViewState) o;
        return meetingsViewStateItems.equals(that.meetingsViewStateItems) && hourFilterItemStateViews.equals(that.hourFilterItemStateViews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingsViewStateItems, hourFilterItemStateViews);
    }

    @Override
    public String toString() {
        return "MeetingViewState{" +
            "meetingsViewStateItems=" + meetingsViewStateItems +
            ", hourFilterItemViewStates=" + hourFilterItemStateViews +
            '}';
    }
}
