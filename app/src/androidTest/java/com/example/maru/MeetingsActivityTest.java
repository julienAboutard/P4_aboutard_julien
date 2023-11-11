package com.example.maru;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.meetings.MeetingsActivity;
import com.example.maru.utils.DrawableMatcher;
import com.example.maru.utils.MyViewAction;
import com.example.maru.utils.RecyclerViewItemAssertion;
import com.example.maru.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingsActivityTest {

    private static final String FIRST_TOPIC = "Test topic 1";
    private static final Room FIRST_ROOM = Room.ROY;
    private static final LocalTime FIRST_TIME = LocalTime.of(10, 0);
    private static final String FIRST_MAIL_LIST = "test@test.com, test1@test.com";

    private static final String SECOND_TOPIC = "Test topic 2";
    private static final Room SECOND_ROOM = Room.IKE;
    private static final LocalTime SECOND_TIME = LocalTime.of(22, 45);
    private static final String SECOND_MAIL_LIST = "test2@test.com, test3@test.com, test4@test.com, test5@test.com";

    private static final String THIRD_TOPIC = "Test topic 3";
    private static final Room THIRD_ROOM = Room.LUCINA;
    private static final LocalTime THIRD_TIME = LocalTime.of(12, 30);
    private static final String THIRD_MAIL_LIST = "test4@test.com, test5@test.com, test7@test.com";

    private static final String FOURTH_TOPIC = "Test topic 1";
    private static final Room FOURTH_ROOM = Room.MARTH;
    private static final LocalTime FOURTH_TIME = LocalTime.of(10, 15);
    private static final String FOURTH_MAIL_LIST = "test6@test.com";

    private static final String FIFTH_TOPIC = "Test topic 3";
    private static final Room FIFTH_ROOM = Room.ROY;
    private static final LocalTime FIFTH_TIME = LocalTime.of(8, 10);
    private static final String FIFTH_MAIL_LIST = "test2@test.com, test3@test.com, test4@test.com, test5@test.com";

    private MeetingsActivity meetingsTest;


    @Before
    public void setUp() {
        ActivityScenario<MeetingsActivity> activityScenario = ActivityScenario.launch(MeetingsActivity.class);
        activityScenario.onActivity(activity -> meetingsTest = activity);
    }

    @After
    public void tearDown() {
        meetingsTest = null;
    }

    @Test
    public void addDeleteFilterMeeting() throws InterruptedException {

        // Delete All Meetings from generate Meetings for Debug Mode
        deleteAllMeetings();

        // Add Meeting
        addMeeting(FIRST_TOPIC, FIRST_MAIL_LIST, FIRST_ROOM, FIRST_TIME);

        assertItemDetailAtPosition(0, FIRST_TOPIC, FIRST_MAIL_LIST, FIRST_ROOM, FIRST_TIME);
        assertItemInRecyclerView(0, FIRST_TOPIC, FIRST_MAIL_LIST, FIRST_ROOM, FIRST_TIME);

        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(1));

        addMeeting(SECOND_TOPIC, SECOND_MAIL_LIST, SECOND_ROOM, SECOND_TIME);
        assertItemDetailAtPosition(1, SECOND_TOPIC, SECOND_MAIL_LIST, SECOND_ROOM, SECOND_TIME);
        assertItemInRecyclerView(1, SECOND_TOPIC, SECOND_MAIL_LIST, SECOND_ROOM, SECOND_TIME);

        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(2));

        // Delete Meeting
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));

        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(1));
        assertItemDetailAtPosition(0, SECOND_TOPIC, SECOND_MAIL_LIST, SECOND_ROOM, SECOND_TIME);
        assertItemInRecyclerView(0, SECOND_TOPIC, SECOND_MAIL_LIST, SECOND_ROOM, SECOND_TIME);

        // Clean Recycler
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));

        // Filter Room
        addMeeting(FIRST_TOPIC, FIRST_MAIL_LIST, FIRST_ROOM, FIRST_TIME);
        addMeeting(SECOND_TOPIC, SECOND_MAIL_LIST, SECOND_ROOM, SECOND_TIME);
        addMeeting(THIRD_TOPIC, THIRD_MAIL_LIST, THIRD_ROOM, THIRD_TIME);

        onView(withId(R.id.room_filter)).perform(click());
        onView(withId(R.id.room_filter_rv)).perform(actionOnItemAtPosition(1, click()));

        assertItemDetailAtPosition(0, THIRD_TOPIC, THIRD_MAIL_LIST, THIRD_ROOM, THIRD_TIME);
        assertItemInRecyclerView(0, THIRD_TOPIC, THIRD_MAIL_LIST, THIRD_ROOM, THIRD_TIME);

        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(1));

        onView(withId(R.id.room_filter_rv)).perform(actionOnItemAtPosition(1, click()));

        //Filter Hour
        addMeeting(FOURTH_TOPIC, FOURTH_MAIL_LIST, FOURTH_ROOM, FOURTH_TIME);

        onView(withId(R.id.hour_filter)).perform(click());
        onView(withId(R.id.hour_filter_rv)).perform(actionOnItemAtPosition(2, click()));

        assertItemDetailAtPosition(0, FIRST_TOPIC, FIRST_MAIL_LIST, FIRST_ROOM, FIRST_TIME);
        assertItemDetailAtPosition(1, FOURTH_TOPIC, FOURTH_MAIL_LIST, FOURTH_ROOM, FOURTH_TIME);

        assertItemInRecyclerView(0, FIRST_TOPIC, FIRST_MAIL_LIST, FIRST_ROOM, FIRST_TIME);
        assertItemInRecyclerView(1, FOURTH_TOPIC, FOURTH_MAIL_LIST, FOURTH_ROOM, FOURTH_TIME);
        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(2));

        onView(withId(R.id.hour_filter_rv)).perform(actionOnItemAtPosition(2, click()));

        // Filter Hour and Room
        addMeeting(FIFTH_TOPIC, FIFTH_MAIL_LIST, FIFTH_ROOM, FIFTH_TIME);

        onView(withId(R.id.room_filter_rv)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.hour_filter_rv)).perform(actionOnItemAtPosition(1, click()));

        assertItemDetailAtPosition(0, FIFTH_TOPIC, FIFTH_MAIL_LIST, FIFTH_ROOM, FIFTH_TIME);
        assertItemInRecyclerView(0, FIFTH_TOPIC, FIFTH_MAIL_LIST, FIFTH_ROOM, FIFTH_TIME);

        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(1));

        onView(withId(R.id.room_filter_rv)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.room_filter_rv)).perform(actionOnItemAtPosition(5, click()));

        onView(withId(R.id.meeting_rv)).check(new RecyclerViewItemCountAssertion(0));
    }


    private void addMeeting(
        @NonNull final String topic,
        @NonNull final String mailList,
        @NonNull final Room room,
        @NonNull final LocalTime time
    ) {
        onView(withId(R.id.floatbtn)).perform(click());

        AddMeetingActivityTest.setMeetingTopic(topic);
        AddMeetingActivityTest.setMeetingTime(time);
        AddMeetingActivityTest.setMeetingRoom(room);
        AddMeetingActivityTest.setMeetingMailList(mailList);

        onView(withId(R.id.meeting_button_add)).perform(click());
    }

    private void assertItemDetailAtPosition(
        int position,
        @NonNull String topic,
        @NonNull String mailList,
        @NonNull Room room,
        @NonNull LocalTime time
        ) {

        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(position, click()));
        onView(withId(R.id.meeting_topic_detail))
            .check(matches(
                withText(
                    String.format(
                        "Topic : %s",
                        topic
                    )
                )));
        onView(withId(R.id.meeting_mail_detail)).check(matches(withText(mailList)));
        onView(withId(R.id.meeting_room_name_detail)).check(matches(withText(room.getName())));
        onView(withId(R.id.meeting_time_detail))
            .check(matches(
                withText(
                    String.format(
                        "Meeting hour : %02d:%02d",
                        time.getHour(),
                        time.getMinute()
                    )

                )
            ));
        pressBack();
    }

    private void assertItemInRecyclerView(
        int positionOnRecyclerView,
        @NonNull String topic,
        @NonNull String mailList,
        @NonNull Room room,
        @NonNull LocalTime time
    ) {
        onView(withId(R.id.meeting_rv)).check(
            new RecyclerViewItemAssertion(
                positionOnRecyclerView,
                R.id.meeting_item_topic_time_room,
                withText(topic + " - " + time.toString() + " - " + meetingsTest.getString(room.getName()))
            )
        );
        onView(withId(R.id.meeting_rv)).check(
            new RecyclerViewItemAssertion(
                positionOnRecyclerView,
                R.id.meeting_item_attendee,
                withText(mailList)
            )
        );
    }

    private void deleteAllMeetings() {
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));
        onView(withId(R.id.meeting_rv)).perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_icon)));
    }
}
