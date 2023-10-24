package com.example.maru.meetingadd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.ViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;

public class AddMeetingActivity extends AppCompatActivity {

    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addmeeting_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

        TextInputEditText roomEditText = findViewById(R.id.meeting_room_add_tiet);
        TextInputEditText timeEditText = findViewById(R.id.meeting_time_add_tiet);
        TextInputEditText topicEditText = findViewById(R.id.meeting_topic_add_tiet);
        TextInputEditText mailEditText = findViewById(R.id.meeting_mail_add_tiet);
        Button addMeetingButton = findViewById(R.id.meeting_button_add);

        bindAddButton(viewModel, roomEditText, timeEditText, topicEditText, mailEditText, addMeetingButton);
        viewModel.getCloseActivitySingleLiveEvent().observe(this, aVoid -> finish());

    }

    private void bindAddButton(AddMeetingViewModel viewModel, TextInputEditText roomEditText, TextInputEditText timeEditText, TextInputEditText topicEditText, TextInputEditText mailEditText, Button addMeetingButton) {
        //noinspection ConstantConditions
        addMeetingButton.setOnClickListener(v -> viewModel.onAddButtonClicked(
                roomEditText.getText().toString(),
                topicEditText.getText().toString(),
                timeEditText.getText().toString(),
                mailEditText.getText().toString()
        ));
        //viewModel.getIsSaveButtonEnabledLiveData().observe(this, isSaveButtonEnabled -> addMeetingButton.setEnabled(isSaveButtonEnabled));
    }
}
