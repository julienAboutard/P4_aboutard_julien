package com.example.maru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.meetinglist.MeetingsAdapter;
import com.example.maru.meetinglist.MeetingsViewModel;
import com.example.maru.meetinglist.OnMeetingClickedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        FloatingActionButton fab = findViewById(R.id.floatbtn);
        MeetingsViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.meeting_rv);

        MeetingRepository meetingRepository = new MeetingRepository();
        meetingRepository.generateRandomMeetings();

        final MeetingsAdapter adapter = new MeetingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        /*MeetingsAdapter adapter = new MeetingsAdapter(new OnMeetingClickedListener() {

            @Override
            public void onMeetingClicked(long neighbourId) {

            }

            @Override
            public void onMeetingAddClicked() {
                viewModel.onAddMeetingClicked();
            }

            @Override
            public void onDeleteMeetingClicked(long neighbourId) {
                viewModel.onDeleteMeetingClicked(neighbourId);
            }
        });*/


        viewModel.getMeetingLiveData().observe(this, new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                adapter.setMeetings(meetings);
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
