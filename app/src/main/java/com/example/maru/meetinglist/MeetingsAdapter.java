package com.example.maru.meetinglist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.maru.R;
import com.example.maru.databinding.MeetingItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MeetingsAdapter extends ListAdapter<MeetingsViewStateItem, MeetingsAdapter.ViewHolder> {

    List<MeetingsViewStateItem> meetings = new ArrayList<>();
    private final OnMeetingClickedListener listener;

    public MeetingsAdapter(OnMeetingClickedListener listener) {
        super(new ListMeetingItemCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            MeetingItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
            ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MeetingItemBinding binding;

        public ViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MeetingsViewStateItem item, OnMeetingClickedListener listener) {
            itemView.setOnClickListener(v -> listener.onMeetingClicked(item.getId()));
            Glide.with(binding.roomImage)
                .load(item.getRoom().getIcon())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.roomImage);
            binding.meetingTopic.setText(
                binding.meetingTopic.getContext().getString(
                    R.string.meeting_topic,
                    item.getTopic(),
                    item.getTime(),
                    binding.meetingTopic.getContext().getString(item.getRoom().getName())
                )
            );

            binding.meetingAttendee.setText(item.getMail_list());
            binding.deleteIcon.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
        }
    }

    private static class ListMeetingItemCallback extends DiffUtil.ItemCallback<MeetingsViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull MeetingsViewStateItem oldItem, @NonNull MeetingsViewStateItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingsViewStateItem oldItem, @NonNull MeetingsViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
