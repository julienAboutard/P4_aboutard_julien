package com.example.maru.filter.room;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;


public class RoomFilterAdapter extends ListAdapter<RoomFilterViewStateItem, RoomFilterAdapter.ViewHolder> {

    @NonNull
    private final OnRoomSelectedListener listener;

    public RoomFilterAdapter(@NonNull OnRoomSelectedListener listener) {
        super();
        this.listener = listener
    }

    @NonNull
    @Override
    public RoomFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomFilterAdapter.ViewHolder holder, int position) {

    }
    
}
