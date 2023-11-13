package com.example.maru.meetingadd.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.maru.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<SpinnerItem> {

    /**
     * Create a spinner to let the user choose the room he wants
     * @param context Add activity
     * @param spinnerItemArrayList List of Room for the spinner
     */
    public SpinnerAdapter(@NonNull Context context, ArrayList<SpinnerItem> spinnerItemArrayList) {
        super(context, 0, spinnerItemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_room_layout_ressource, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.spinner_name_room_item);
        ImageView imageView = convertView.findViewById(R.id.spinner_icon_room_item);
        SpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            textView.setText(currentItem.getRoom().getName());
            imageView.setImageResource(currentItem.getRoom().getIcon());
        }
        return  convertView;
    }
}
