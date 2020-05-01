package ru.sulgik.remotearduino.modules.database.bases;

import android.widget.ProgressBar;

import androidx.annotation.Nullable;

public interface OnUpdateListener{

    void onUpdate(@Nullable LocationReference reference);

}
