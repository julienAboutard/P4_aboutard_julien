package com.example.maru.meetingadd;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.ViewModelFactory;
import com.example.maru.data.Room;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalTime;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity {

    public static Intent navigate(Context context) {
        return new Intent(context, AddMeetingActivity.class);
    }

    private TextInputEditText timeEditText;

    private LocalTime time = LocalTime.now();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.addmeeting_activity);
        initToolbar();

        AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

        Spinner mySpinner = findViewById(R.id.spinner_room_name);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Room.values()));

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) parent.getItemAtPosition(position);

                viewModel.onRoomSelected(room);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timeEditText = findViewById(R.id.meeting_time_add_tiet);



        TextInputEditText topicEditText = findViewById(R.id.meeting_topic_add_tiet);
        TextInputEditText mailEditText = findViewById(R.id.meeting_mail_add_tiet);
        Button addMeetingButton = findViewById(R.id.meeting_button_add);

        addMeetingButton.setOnClickListener(v -> viewModel.onAddButtonClicked(
            topicEditText.getText().toString(),
            mailEditText.getText().toString())
        );

        viewModel.getCloseActivitySingleLiveEvent().observe(this, aVoid -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popTimePicker (View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, hourOfDay, minute) -> {
            AddMeetingViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddMeetingViewModel.class);

            time = LocalTime.of(hourOfDay, minute);
            viewModel.onSelectedTime(time);

            timeEditText.setText(String.format(Locale.getDefault(), "%02d:%02d", time.getHour(), time.getMinute()));

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, time.getHour(), time.getMinute(), true);
        timePickerDialog.show();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
