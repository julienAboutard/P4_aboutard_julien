package com.example.maru.meetinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.data.Meeting;
import com.example.maru.data.MeetingRepository;
import com.example.maru.databinding.MeetingItemBinding;

public class MeetingsAdapter extends ListAdapter<Meeting, MeetingsAdapter.ViewHolder> {

    private final OnMeetingClickedListener listener;

    public MeetingsAdapter(OnMeetingClickedListener listener) {
        super(new ListMeetingItemCallback());

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MeetingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MeetingItemBinding binding;
        /*private final TextView topicTextView;
        private final TextView roomTextView;
        private final TextView timeTextView;
        private final TextView mailTextView;
        private final ImageView deleteImageView;*/

        public ViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());
            this.binding =  binding;

            /*topicTextView = itemView.findViewById(R.id.meeting_topic);
            roomTextView = itemView.findViewById(R.id.room);
            timeTextView = itemView.findViewById(R.id.time);
            mailTextView = itemView.findViewById(R.id.mail_list);
            deleteImageView = itemView.findViewById(R.id.delete_icon);*/
        }

        public void bind(Meeting item, OnMeetingClickedListener listener) {
//            itemView.setOnClickListener(v -> listener.onMeetingClicked(item.getId()));
            binding.meetingTopic.setText(item.getTopic());
            binding.room.setText(item.getRoom());
            binding.time.setText(item.getTime());
            binding.mailList.setText(item.getMail_list());
            binding.deleteIcon.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
        }
    }

    private static class ListMeetingItemCallback extends DiffUtil.ItemCallback<Meeting> {
        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.equals(newItem);
        }
    }
}
