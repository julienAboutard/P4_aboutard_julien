package com.example.maru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.view.View;

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
        MeetingsViewModel viewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelFactory.getInstance()).get(MeetingsViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.meeting_rv);
        MeetingsAdapter adapter = new MeetingsAdapter(new OnMeetingClickedListener() {

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
        });
        recyclerView.setAdapter(adapter);

    }



}