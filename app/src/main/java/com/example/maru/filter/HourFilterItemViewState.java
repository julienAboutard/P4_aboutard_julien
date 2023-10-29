package com.example.maru.filter;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.Objects;

public class HourFilterItemViewState {

    @NonNull
    private final LocalTime hourLocalTime;

    @NonNull
    private final String hour;

    public HourFilterItemViewState(
            @NonNull LocalTime hourLocalTime,
            @NonNull String hour
    ) {
        this.hourLocalTime = hourLocalTime;
        this.hour = hour;
    }

    @NonNull
    public LocalTime getHourLocalTime() {
        return hourLocalTime;
    }

    @NonNull
    public String getHour() {
        return hour;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HourFilterItemViewState that = (HourFilterItemViewState) o;
        return hour.equals(that.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour);
    }

    @NonNull
    @Override
    public String toString() {
        return "MeetingViewStateHourFilterItem{" +
                "hour='" + hour + '\'' +
                '}';
    }
}
