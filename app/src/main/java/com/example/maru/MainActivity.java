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
import android.view.MenuItem;
import android.view.View;

import com.example.maru.filter.HourFilterAdapter;
import com.example.maru.meetingadd.AddMeetingActivity;
import com.example.maru.meetingdetail.MeetingDetailActivity;
import com.example.maru.meetinglist.MeetingsAdapter;
import com.example.maru.meetinglist.OnMeetingClickedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initToolbar();

        final FloatingActionButton fab = findViewById(R.id.floatbtn);
        fab.setOnClickListener(v -> startActivities(new Intent[]{AddMeetingActivity.navigate(this)}));


        MeetingsViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingsViewModel.class);
        RecyclerView mettingRecyclerView = findViewById(R.id.meeting_rv);
        mettingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        final HourFilterAdapter hourAdapter = new HourFilterAdapter();
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.room_filter) {
            setVisibility(findViewById(R.id.room_filter_rv));
            return true;
        } else if (itemId == R.id.hour_filter) {
            setVisibility(findViewById(R.id.hour_filter_rv));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setVisibility(@NonNull View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
