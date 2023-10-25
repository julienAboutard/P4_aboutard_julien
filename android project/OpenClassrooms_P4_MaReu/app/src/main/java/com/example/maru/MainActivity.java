package com.example.maru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.maru.data.MeetingRepository;
import com.example.maru.meetingadd.AddMeetingActivity;
import com.example.maru.meetingdetail.MeetingDetailActivity;
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

//        MeetingRepository meetingRepository = new MeetingRepository();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(v -> startActivities(new Intent[]{AddMeetingActivity.navigate(this)}));
        MeetingsAdapter adapter = new MeetingsAdapter(new OnMeetingClickedListener() {

            @Override
            public void onMeetingClicked(long meetingId) {
                startActivities(new Intent[]{MeetingDetailActivity.navigate(getApplicationContext(), meetingId)});
            }
            @Override
            public void onDeleteMeetingClicked(long meetingId) {
                viewModel.onDeleteMeetingClicked(meetingId);
            }
        });

        recyclerView.setAdapter(adapter);
        viewModel.getMeetingLiveData().observe(this, meetings -> {
            adapter.submitList(meetings);
        });
    }



}
