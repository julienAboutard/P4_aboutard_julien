package com.example.maru;

import static org.junit.Assert.fail;

import androidx.lifecycle.LiveData;

public class LiveDataTestUtils {

    public static <T> T getValueTesting(LiveData<T> liveData) {

        liveData.observeForever(value -> {
        });

        return liveData.getValue();
    }

}
