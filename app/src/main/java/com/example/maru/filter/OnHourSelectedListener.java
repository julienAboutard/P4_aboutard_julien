package com.example.maru.filter;

import androidx.annotation.NonNull;

import java.time.LocalTime;

public interface OnHourSelectedListener {
    void onHourSelected(@NonNull LocalTime hour);
}
