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

import java.util.ArrayList;
import java.util.List;

public class MeetingsAdapter extends ListAdapter<MeetingsViewStateItem,MeetingsAdapter.ViewHolder> {

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
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.meeting_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MeetingsViewStateItem item = meetings.get(position);
        holder.topicTextView.setText(item.getTopic());
        holder.roomTextView.setText(item.getRoom());
        holder.timeTextView.setText(item.getTime());
        //holder.mailTextView.setText(item.getMail_list());
        //holder.deleteImageView.setOnClickListener(v -> holder.onDeleteMeetingClicked(item.getId()));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        private final MeetingItemBinding binding;
        private final TextView topicTextView;
        private final TextView roomTextView;
        private final TextView timeTextView;
        private final TextView mailTextView;
        private final ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            /*super(binding.getRoot());
            this.binding =  binding;*/

            super(itemView);

            topicTextView = itemView.findViewById(R.id.meeting_topic);
            roomTextView = itemView.findViewById(R.id.room);
            timeTextView = itemView.findViewById(R.id.time);
            mailTextView = itemView.findViewById(R.id.mail_list);
            deleteImageView = itemView.findViewById(R.id.delete_icon);
        }

        public void bind(MeetingsViewStateItem item, OnMeetingClickedListener listener) {
//            itemView.setOnClickListener(v -> listener.onMeetingClicked(item.getId()));
            topicTextView.setText(item.getTopic());
            roomTextView.setText(item.getRoom());
            timeTextView.setText(item.getTime());
            //mailTextView.setText(item.getMail_list());
            deleteImageView.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
        }
    }

    public void setMeetings(List<MeetingsViewStateItem> meetings) {
        this.meetings = meetings;
        notifyDataSetChanged();
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
