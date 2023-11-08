package com.example.maru.filter.hour;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.Objects;

public class HourFilterViewStateItem {

    @NonNull
    private final LocalTime hourLocalTime;

    @ColorRes
    private final int colorText;

    public HourFilterViewStateItem(@NonNull LocalTime hourLocalTime, int colorText) {
        this.hourLocalTime = hourLocalTime;
        this.colorText = colorText;
    }

    @NonNull
    public LocalTime getHourLocalTime() {
        return hourLocalTime;
    }


    @ColorRes
    public int getColorText() {
        return colorText;
    }

    @NonNull
    @Override
    public String toString() {
        return "HourFilterItemStateView{" +
            "hourLocalTime=" + hourLocalTime +
            ", colorText=" + colorText +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HourFilterViewStateItem that = (HourFilterViewStateItem) o;
        return colorText == that.colorText && hourLocalTime.equals(that.hourLocalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hourLocalTime, colorText);
    }

}
