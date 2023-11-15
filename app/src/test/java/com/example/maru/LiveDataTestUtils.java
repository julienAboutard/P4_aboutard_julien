package com.example.maru;

import androidx.lifecycle.LiveData;

public class LiveDataTestUtils {

    public static <T> T getValueTesting(LiveData<T> liveData) {

        liveData.observeForever(value -> {
        });

        return liveData.getValue();
    }

}
