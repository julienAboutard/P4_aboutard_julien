package com.example.maru.meetings;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.LiveDataTestUtils;

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
public class MeetingsViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private MeetingRepository meetingRepository;

    private MeetingsViewModel viewModel;

    @Before
    public void setUp() {

        MutableLiveData<List<Meeting>> meetingsMutableLiveData = new MutableLiveData<>();

        given(meetingRepository.getMeetingsLiveData()).willReturn(meetingsMutableLiveData);

        List<Meeting> meetings = getTestMeetings();
        meetingsMutableLiveData.setValue(meetings);


        viewModel = new MeetingsViewModel(meetingRepository);

        verify(meetingRepository).getMeetingsLiveData();
    }

    @Test
    public void assertLiveData() {
        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(5, testList.getMeetingsViewStateItems().size());

        assertFirstMeeting(testList.getMeetingsViewStateItems(), 0);
        assertSecondMeeting(testList.getMeetingsViewStateItems(), 1);
        assertThirdMeeting(testList.getMeetingsViewStateItems(),2);
        assertFourthMeeting(testList.getMeetingsViewStateItems(), 3);
        assertFifthMeeting(testList.getMeetingsViewStateItems(), 4);

        assertEquals(9, testList.getHourFilterViewStateItems().size());
        assertEquals(6, testList.getRoomFilterViewStateItems().size());

    }

    @Test
    public void assertOneHourFilterSelected() {
        viewModel.onHourSelected(LocalTime.of(8, 0));

        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(1, testList.getMeetingsViewStateItems().size());
        assertFifthMeeting(testList.getMeetingsViewStateItems(), 0);

    }

    @Test
    public void assertTwoHoursSelected() {
        viewModel.onHourSelected(LocalTime.of(8, 0));
        viewModel.onHourSelected(LocalTime.of(22,0));

        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(2, testList.getMeetingsViewStateItems().size());
        assertSecondMeeting(testList.getMeetingsViewStateItems(), 0);
        assertFifthMeeting(testList.getMeetingsViewStateItems(), 1);

    }

    @Test
    public void assertOneRoomSelected() {
        viewModel.onRoomSelected(Room.IKE);

        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(1, testList.getMeetingsViewStateItems().size());
        assertSecondMeeting(testList.getMeetingsViewStateItems(), 0);
    }

    @Test
    public void assertTwoRoomsSelected() {
        viewModel.onRoomSelected(Room.IKE);
        viewModel.onRoomSelected(Room.ROY);

        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(3, testList.getMeetingsViewStateItems().size());
        assertFirstMeeting(testList.getMeetingsViewStateItems(), 0);
        assertSecondMeeting(testList.getMeetingsViewStateItems(), 1);
        assertFifthMeeting(testList.getMeetingsViewStateItems(), 2);
    }

    @Test
    public void assertOneRoomOneHourSelected() {
        viewModel.onRoomSelected(Room.ROY);
        viewModel.onHourSelected(LocalTime.of(10,0));

        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(1, testList.getMeetingsViewStateItems().size());
        assertFirstMeeting(testList.getMeetingsViewStateItems(), 0);
    }

    @Test
    public void assertTwoRoomsTwoHoursSelected() {
        viewModel.onRoomSelected(Room.ROY);
        viewModel.onRoomSelected(Room.LUCINA);
        viewModel.onHourSelected(LocalTime.of(10,0));
        viewModel.onHourSelected(LocalTime.of(12,0));

        MeetingsViewState testList = LiveDataTestUtils.getValueTesting(viewModel.getListFilterMeetings());

        assertEquals(2, testList.getMeetingsViewStateItems().size());
        assertFirstMeeting(testList.getMeetingsViewStateItems(), 0);
        assertThirdMeeting(testList.getMeetingsViewStateItems(), 1);
    }

    @Test
    public void deleteMeeting() {
        viewModel.onDeleteMeetingClicked(2);

        verify(meetingRepository).deleteMeeting(2);

    }


    @NonNull
    private List<Meeting> getTestMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        String mailList1 = "test@test.com, test1@test.com";
        String mailList2 = "test2@test.com, test3@test.com, test4@test.com, test5@test.com";
        String mailList3 = "test4@test.com, test5@test.com, test7@test.com";
        String mailList4 = "test6@test.com";

        meetings.add(new Meeting(0, Room.ROY,  LocalTime.of(10, 0),"Test topic 1", mailList1));
        meetings.add(new Meeting(1, Room.IKE, LocalTime.of(22, 45), "Test topic 2", mailList2));
        meetings.add(new Meeting(2, Room.LUCINA, LocalTime.of(12, 30),"Test topic 3", mailList3));
        meetings.add(new Meeting(3, Room.MARTH, LocalTime.of(10, 15), "Test topic 1", mailList4));
        meetings.add(new Meeting(4, Room.ROY, LocalTime.of(8, 10), "Test topic 3", mailList2));

        return meetings;
    }

    private void assertFirstMeeting(@NonNull List<MeetingsViewStateItem> listTest, int position) {
        assertEquals("Test topic 1", listTest.get(position).getTopic());
        assertEquals(LocalTime.of(10,0), listTest.get(position).getTime());
        assertEquals(Room.ROY.getIcon(), listTest.get(position).getRoom().getIcon());
        assertEquals(0, listTest.get(position).getId());
        assertEquals("test@test.com, test1@test.com", listTest.get(position).getMail_list());
    }

    private void assertSecondMeeting(@NonNull List<MeetingsViewStateItem> listTest, int position) {
        assertEquals("Test topic 2", listTest.get(position).getTopic());
        assertEquals(LocalTime.of(22,45), listTest.get(position).getTime());
        assertEquals(Room.IKE.getIcon(), listTest.get(position).getRoom().getIcon());
        assertEquals(1, listTest.get(position).getId());
        assertEquals("test2@test.com, test3@test.com, test4@test.com, test5@test.com", listTest.get(position).getMail_list());
    }

    private void assertThirdMeeting(@NonNull List<MeetingsViewStateItem> listTest, int position) {
        assertEquals("Test topic 3", listTest.get(position).getTopic());
        assertEquals(LocalTime.of(12,30), listTest.get(position).getTime());
        assertEquals(Room.LUCINA.getIcon(), listTest.get(position).getRoom().getIcon());
        assertEquals(2, listTest.get(position).getId());
        assertEquals("test4@test.com, test5@test.com, test7@test.com", listTest.get(position).getMail_list());
    }

    private void assertFourthMeeting(@NonNull List<MeetingsViewStateItem> listTest, int position) {
        assertEquals("Test topic 1", listTest.get(position).getTopic());
        assertEquals(LocalTime.of(10,15), listTest.get(position).getTime());
        assertEquals(Room.MARTH.getIcon(), listTest.get(position).getRoom().getIcon());
        assertEquals(3, listTest.get(position).getId());
        assertEquals("test6@test.com", listTest.get(position).getMail_list());
    }

    private void assertFifthMeeting(@NonNull List<MeetingsViewStateItem> listTest, int position) {
        assertEquals("Test topic 3", listTest.get(position).getTopic());
        assertEquals(LocalTime.of(8,10), listTest.get(position).getTime());
        assertEquals(Room.ROY.getIcon(), listTest.get(position).getRoom().getIcon());
        assertEquals(4, listTest.get(position).getId());
        assertEquals("test2@test.com, test3@test.com, test4@test.com, test5@test.com", listTest.get(position).getMail_list());
    }
}
