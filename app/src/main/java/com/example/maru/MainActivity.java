package com.example.maru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.maru.data.MeetingRepository;
import com.example.maru.data.Room;
import com.example.maru.filter.HourFilterAdapter;
import com.example.maru.filter.OnHourSelectedListener;
import com.example.maru.meetingadd.AddMeetingActivity;
import com.example.maru.meetingdetail.MeetingDetailActivity;
import com.example.maru.meetinglist.MeetingsAdapter;
import com.example.maru.meetinglist.MeetingsViewModel;
import com.example.maru.meetinglist.OnMeetingClickedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initToolbar();

        FloatingActionButton fab = findViewById(R.id.floatbtn);
        MeetingsViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);

        RecyclerView mettingRecyclerView = findViewById(R.id.meeting_rv);

        mettingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        mettingRecyclerView.setAdapter(adapter);
        viewModel.getMeetingLiveData().observe(this, meetings -> {
            adapter.submitList(meetings);
        });

        final HourFilterAdapter hourAdapter = new HourFilterAdapter(new OnHourSelectedListener() {
            @Override
            public void onHourSelected(@NonNull LocalTime hour) {
                
            }
        });
        RecyclerView hourFilterRecyclerView = findViewById(R.id.hour_filter_rv);
        hourFilterRecyclerView.setAdapter(hourAdapter);

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meeting_toolbar_menu, menu);
        return true;
    }

}
