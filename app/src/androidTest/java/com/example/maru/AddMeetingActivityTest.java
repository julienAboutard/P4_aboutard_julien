package com.example.maru;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;



import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.maru.meetingadd.AddMeetingActivity;
import com.example.maru.data.Room;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalTime;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddMeetingActivityTest {

    private AddMeetingActivity addTest;

    @Before
    public void setUp() {
        ActivityScenario<AddMeetingActivity> addScenario = ActivityScenario.launch(AddMeetingActivity.class);
        addScenario.onActivity(activity -> addTest = activity);
    }

    @Test
    public void addMeeting() {

       setMeetingRoom(Room.LUCINA);
       setMeetingTime(LocalTime.of(8,0));
       setMeetingTopic("Test");
       setMeetingMailList("test@test.com, test1@test.com");

        onView(withId(R.id.meeting_button_add)).perform(click());

        assertTrue(addTest.isFinishing());
    }

    public static void setMeetingTopic(@NonNull String topic) {
        onView(
            withId(R.id.meeting_topic_add_tiet)
        ).perform(
            replaceText(topic),
            closeSoftKeyboard()
        );
    }

    public static void setMeetingMailList(@NonNull String mailList) {
        onView(
            withId(R.id.meeting_mail_add_tiet)
        ).perform(
            replaceText(mailList),
            closeSoftKeyboard()
        );
    }

    public static void setMeetingRoom(@NonNull Room room) {
        onView(withId(R.id.spinner_room)).perform(click());
        onView(allOf(withId(R.id.spinner_name_room_item), withText(room.getName()))).perform(click());
    }

    public static void setMeetingTime(@NonNull LocalTime time) {

        onView(withId(R.id.meeting_time_add_til)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(time.getHour(), time.getMinute()));
        onView(withId(android.R.id.button1)).perform(click());
    }

}
