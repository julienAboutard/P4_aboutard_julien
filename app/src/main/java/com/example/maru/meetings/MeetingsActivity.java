package com.example.maru.meetings;

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

import com.example.maru.R;
import com.example.maru.ViewModelFactory;
import com.example.maru.filter.hour.HourFilterAdapter;
import com.example.maru.filter.room.RoomFilterAdapter;
import com.example.maru.meetingadd.AddMeetingActivity;
import com.example.maru.meetingdetail.MeetingDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeetingsActivity extends AppCompatActivity {


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
        MeetingsAdapter meetingsAdapter = new MeetingsAdapter(new OnMeetingClickedListener() {

            @Override
            public void onMeetingClicked(long meetingId) {
                startActivities(new Intent[]{MeetingDetailActivity.navigate(getApplicationContext(), meetingId)});
            }

            @Override
            public void onDeleteMeetingClicked(long meetingId) {
                viewModel.onDeleteMeetingClicked(meetingId);
            }
        });

        mettingRecyclerView.setAdapter(meetingsAdapter);

        final HourFilterAdapter hourAdapter = new HourFilterAdapter(viewModel::onHourSelected);
        RecyclerView hourFilterRecyclerView = findViewById(R.id.hour_filter_rv);
        hourFilterRecyclerView.setAdapter(hourAdapter);

        final RoomFilterAdapter roomAdapter = new RoomFilterAdapter(viewModel::onRoomSelected);
        RecyclerView roomFilterRecyclerView = findViewById(R.id.room_filter_rv);
        roomFilterRecyclerView.setAdapter(roomAdapter);

        viewModel.getListFilterMeetings().observe(this, viewState -> {
            meetingsAdapter.submitList(viewState.getMeetingsViewStateItems());
            hourAdapter.submitList(viewState.getHourFilterViewStateItems());
            roomAdapter.submitList(viewState.getRoomFilterViewStateItems());
        });

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
