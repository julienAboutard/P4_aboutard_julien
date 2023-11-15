package com.example.maru.meetingadd;

import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;


import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AddMeetingViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private AddMeetingViewModel viewModel;
    private final LocalTime time = LocalTime.now();
    private final Room room = Room.LUCINA;

    @Before
    public void setUp() {
        MutableLiveData<List<Meeting>> meetingsMutableLiveData = new MutableLiveData<>();

        List<Meeting> meetings = new ArrayList<>();
        meetingsMutableLiveData.setValue(meetings);

        viewModel = new AddMeetingViewModel(meetingRepository);
    }

    @Test
    public void assertAddButtonClicked() {
        viewModel.onRoomSelected(room);
        viewModel.onSelectedTime(time);
        String topic = "Test 1";
        String mailList = "test@test.com";
        viewModel.onAddButtonClicked(topic, mailList);

        verify(meetingRepository).addMeeting(room, time, topic, mailList);

    }
}
