package com.example.maru.filter;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.Objects;

public class HourFilterItemStateView {

    @NonNull
    private final LocalTime hourLocalTime;

    @ColorRes
    private final int colorText;

    public HourFilterItemStateView(@NonNull LocalTime hourLocalTime, int colorText) {
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
        HourFilterItemStateView that = (HourFilterItemStateView) o;
        return colorText == that.colorText && hourLocalTime.equals(that.hourLocalTime) && hour.equals(that.hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hourLocalTime, colorText);
    }

}
