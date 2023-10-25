package com.example.maru.meetingdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.ViewModelFactory;

public class MeetingDetailActivity extends AppCompatActivity {

    private static final String KEY_MEETING_ID = "KEY_MEETING_ID";

    public static Intent navigate(Context context, long meetingId) {
        Intent intent = new Intent(context, MeetingDetailActivity.class);

        intent.putExtra(KEY_MEETING_ID, meetingId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailmeeting_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView topicTextView = findViewById(R.id.meeting_topic_detail);
        TextView roomTextView = findViewById(R.id.meeting_room_detail);
        TextView timeTextView = findViewById(R.id.meeting_time_detail);
        TextView mailTextView = findViewById(R.id.meeting_mail_detail);

        long meetingId = getIntent().getLongExtra(KEY_MEETING_ID, -1);

        if (meetingId == -1) {
            throw new IllegalStateException("Please use MeetingDetailActivity.navigate() to launch the Activity");
        }

        MeetingDetailViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingDetailViewModel.class);
        viewModel.getMeetingDetailViewStateLiveData(meetingId).observe(this, meetingDetailViewStateItem -> {
            topicTextView.setText(meetingDetailViewStateItem.getTopic());
            roomTextView.setText(meetingDetailViewStateItem.getRoom());
            timeTextView.setText(meetingDetailViewStateItem.getTime());
            mailTextView.setText(meetingDetailViewStateItem.getMail_list());
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
