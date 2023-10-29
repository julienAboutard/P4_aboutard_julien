package com.example.maru.filter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;

public class HourFilterAdapter extends ListAdapter<HourFilterItemViewState, HourFilterAdapter.ViewHolder> {

    @NonNull
    private final OnHourSelectedListener listener;

    public HourFilterAdapter(@NonNull OnHourSelectedListener listener) {
        super(new HourFilterAdapterDiffCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewHour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHour = itemView.findViewById(R.id.hour_item_textview);
        }

        public void bind(@NonNull final HourFilterItemViewState item, @NonNull final OnHourSelectedListener listener) {
            textViewHour.setText(item.getHour());
            textViewHour.setOnClickListener(v -> listener.onHourSelected(item.getHourLocalTime()));
        }
    }

    private static class HourFilterAdapterDiffCallback extends DiffUtil.ItemCallback<HourFilterItemViewState> {
        @Override
        public boolean areItemsTheSame(@NonNull HourFilterItemViewState oldItem, @NonNull HourFilterItemViewState newItem) {
            return oldItem.getHour().equals(newItem.getHour());
        }

        @Override
        public boolean areContentsTheSame(@NonNull HourFilterItemViewState oldItem, @NonNull HourFilterItemViewState newItem) {
            return oldItem.equals(newItem);
        }
    }
}