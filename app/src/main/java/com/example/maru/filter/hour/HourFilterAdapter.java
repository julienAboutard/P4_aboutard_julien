package com.example.maru.filter.hour;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;

import java.util.Locale;

public class HourFilterAdapter extends ListAdapter<HourFilterViewStateItem, HourFilterAdapter.ViewHolder> {

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

        public void bind(@NonNull final HourFilterViewStateItem item, @NonNull OnHourSelectedListener listener) {
            textViewHour.setText(String.format(
                Locale.getDefault(),
                "%02d:%02d",
                item.getHourLocalTime().getHour(),
                item.getHourLocalTime().getMinute()));
            textViewHour.setTextColor(ContextCompat.getColor(textViewHour.getContext(), item.getColorText()));
            textViewHour.setOnClickListener(v -> listener.onHourSelected(item.getHourLocalTime()));
        }
    }

    private static class HourFilterAdapterDiffCallback extends DiffUtil.ItemCallback<HourFilterViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull HourFilterViewStateItem oldItem, @NonNull HourFilterViewStateItem newItem) {
            return oldItem.getHourLocalTime().equals(newItem.getHourLocalTime());
        }

        @Override
        public boolean areContentsTheSame(@NonNull HourFilterViewStateItem oldItem, @NonNull HourFilterViewStateItem newItem) {
            return oldItem.getColorText() == newItem.getColorText();
        }
    }
}
