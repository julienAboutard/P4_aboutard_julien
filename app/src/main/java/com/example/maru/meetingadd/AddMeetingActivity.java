package com.example.maru.meetingadd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.maru.R;
import com.example.maru.ViewModelFactory;
import com.example.maru.data.Room;
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

        Spinner mySpinner = findViewById(R.id.spinner_room_name);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Room.values()));

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Room room = (Room) parent.getItemAtPosition(position);

                viewModel.onRoomSelected(room);

                Toast.makeText(AddMeetingActivity.this,
                    room.getName(),
                    Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        TextInputEditText timeEditText = findViewById(R.id.meeting_time_add_tiet);
        TextInputEditText topicEditText = findViewById(R.id.meeting_topic_add_tiet);
        TextInputEditText mailEditText = findViewById(R.id.meeting_mail_add_tiet);
        Button addMeetingButton = findViewById(R.id.meeting_button_add);

        addMeetingButton.setOnClickListener(v -> viewModel.onAddButtonClicked(
            timeEditText.getText().toString(),
            topicEditText.getText().toString(),
            mailEditText.getText().toString()
        ));

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
}
