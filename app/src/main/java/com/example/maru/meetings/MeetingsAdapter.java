package com.example.maru.meetings;

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

public class MeetingsAdapter extends ListAdapter<MeetingsViewStateItem, MeetingsAdapter.ViewHolder> {

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
            Glide.with(binding.meetingItemRoomIcon)
                .load(item.getRoom().getIcon())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.meetingItemRoomIcon);
            binding.meetingItemTopicTimeRoom.setText(
                binding.meetingItemTopicTimeRoom.getContext().getString(
                    R.string.meeting_quick_detail,
                    item.getTopic(),
                    binding.meetingItemTopicTimeRoom.getContext().getString(R.string.time_format, item.getTime().getHour(), item.getTime().getMinute()),
                    binding.meetingItemTopicTimeRoom.getContext().getString(item.getRoom().getName())
                )
            );

            binding.meetingItemAttendee.setText(item.getMail_list());
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
