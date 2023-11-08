package com.example.maru.meetingdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.maru.R;
import com.example.maru.ViewModelFactory;
import com.example.maru.databinding.DetailmeetingActivityBinding;

public class MeetingDetailActivity extends AppCompatActivity {

    private static final String KEY_MEETING_ID = "KEY_MEETING_ID";

    private DetailmeetingActivityBinding binding;

    public static Intent navigate(Context context, long meetingId) {
        Intent intent = new Intent(context, MeetingDetailActivity.class);

        intent.putExtra(KEY_MEETING_ID, meetingId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DetailmeetingActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initToolbar();

        long meetingId = getIntent().getLongExtra(KEY_MEETING_ID, -1);

        if (meetingId == -1) {
            throw new IllegalStateException("Please use MeetingDetailActivity.navigate() to launch the Activity");
        }

        MeetingDetailViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingDetailViewModel.class);
        viewModel.getMeetingDetailViewStateLiveData(meetingId).observe(this, meetingDetailViewStateItem -> {
            Glide.with(binding.meetingRoomIconDetail)
                .load(meetingDetailViewStateItem.getRoom().getIcon())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.meetingRoomIconDetail);
            binding.meetingTopicDetail.setText(
                binding.meetingTopicDetail.getContext().getString(
                    R.string.detail_topic_format,
                    meetingDetailViewStateItem.getTopic()
                )
            );
            binding.meetingTimeDetail.setText(
                binding.meetingTimeDetail.getContext().getString(
                    R.string.detail_time_format,
                    meetingDetailViewStateItem.getTime().getHour(),
                    meetingDetailViewStateItem.getTime().getMinute()
                )
            );
            binding.meetingRoomNameDetail.setText(meetingDetailViewStateItem.getRoom().getName());
            binding.meetingMailDetail.setText(meetingDetailViewStateItem.getMailList());
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
