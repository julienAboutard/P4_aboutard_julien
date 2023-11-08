package com.example.maru.filter.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.google.android.material.chip.Chip;


public class RoomFilterAdapter extends ListAdapter<RoomFilterViewStateItem, RoomFilterAdapter.ViewHolder> {

    @NonNull
    private final OnRoomSelectedListener listener;

    public RoomFilterAdapter(@NonNull OnRoomSelectedListener listener) {
        super(new RoomFilterAdapterDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate((R.layout.room_item), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Chip roomChip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roomChip = itemView.findViewById(R.id.room_item_chip);
        }

        public void bind(@NonNull final RoomFilterViewStateItem item, @NonNull OnRoomSelectedListener listener) {
            roomChip.setChipIcon(ContextCompat.getDrawable(roomChip.getContext(),item.getRoom().getIcon()));
            roomChip.setText(item.getRoom().getName());
            roomChip.setChecked(item.isSelectedStatus());
            roomChip.setOnCheckedChangeListener((buttonView, isChecked) -> listener.onRoomSelected(item.getRoom()));
        }
    }

    private static class RoomFilterAdapterDiffCallback extends DiffUtil.ItemCallback<RoomFilterViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull RoomFilterViewStateItem oldItem, @NonNull RoomFilterViewStateItem newItem) {
            return oldItem.getRoom().equals(newItem.getRoom());
        }

        @Override
        public boolean areContentsTheSame(@NonNull RoomFilterViewStateItem oldItem, @NonNull RoomFilterViewStateItem newItem) {
            return oldItem.isSelectedStatus() == newItem.isSelectedStatus();
        }
    }
}
