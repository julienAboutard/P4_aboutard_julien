package com.example.maru.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.maru.LiveDataTestUtils;
import com.example.maru.config.BuildConfigResolver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MeetingRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MeetingRepository meetingRepositoryTest;

    @Mock
    private BuildConfigResolver buildConfigResolver;

    @Before
    public void setUp() {
        given(buildConfigResolver.isDebug()).willReturn(false);

        meetingRepositoryTest = new MeetingRepository(buildConfigResolver);

        verify(buildConfigResolver).isDebug();
        verifyNoMoreInteractions(buildConfigResolver);
    }

    @Test
    public void testAddMeeting() {
        String topic = getTopic();
        LocalTime time = getTime();
        Room room = Room.IKE;
        String mailList = getMailList();

        meetingRepositoryTest.addMeeting(
            room,
            time,
            topic,
            mailList
        );

        List<Meeting> result = LiveDataTestUtils.getValueTesting(meetingRepositoryTest.getMeetingsLiveData());

        assertEquals(1, result.size());
        Meeting meetingTest = result.get(0);
        assertEquals(
            meetingTest,
            getMeeting(
                0,
                getTime(),
                Room.IKE
            )
        );
    }

    @Test
    public void testRemoveMeeting() {
        String topic = getTopic();
        LocalTime time = getTime();
        Room room = Room.IKE;
        String mailList = getMailList();

        meetingRepositoryTest.addMeeting(
            room,
            time,
            topic,
            mailList
        );

        meetingRepositoryTest.deleteMeeting(0);
        List<Meeting> result = LiveDataTestUtils.getValueTesting(meetingRepositoryTest.getMeetingsLiveData());

        assertEquals(0, result.size());
    }

    @Test
    public void testIdIncrement() {
        String topic0 = getTopic();
        LocalTime time0 = getTime();
        Room room0 = Room.IKE;
        String mailList0 = getMailList();

        meetingRepositoryTest.addMeeting(
            room0,
            time0,
            topic0,
            mailList0
        );

        String topic1 = getTopic();
        LocalTime time1 = getTime();
        Room room1 = Room.LUCINA;
        String mailList1 = getMailList();

        meetingRepositoryTest.addMeeting(
            room1,
            time1,
            topic1,
            mailList1
        );

        List<Meeting> result = LiveDataTestUtils.getValueTesting(meetingRepositoryTest.getMeetingsLiveData());

        assertEquals(2, result.size());
        assertEquals(result.get(0).getId(), 0);
        assertEquals(result.get(1).getId(), 1);
    }

    private String getTopic() {
        return "meetingTopic";
    }

    private LocalTime getTime() {
        return LocalTime.of(18,00);
    }

    private String getMailList() {
        String mailList = "";
        mailList = "test@mail.com";
        return mailList;
    }


    private Meeting getMeeting(int id, LocalTime time, Room room) {
        return new Meeting(
            id,
            room,
            time,
            getTopic(),
            getMailList()
        );
    }
}
