package com.example.maru.data;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.example.maru.R;

public enum Room {
    CHOOSE(R.string.choose, 0),
    ROY(R.string.roy, R.drawable.ic_room_roy),
    Lucina(R.string.lucina, R.drawable.ic_room_lucina),
    Ike(R.string.ike, R.drawable.ic_room_ike),
    Marth(R.string.marth, R.drawable.ic_room_marth),
    Chrom(R.string.chrom, R.drawable.ic_room_chrom),
    Robin(R.string.robin, R.drawable.ic_room_robin);

    @StringRes
    private final int name;

    @DrawableRes
    private final int icon;

    @StringRes
    public int getName() {
        return name;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    Room(@StringRes int name, @DrawableRes int icon) {
        this.name = name;
        this.icon = icon;
    }

}
