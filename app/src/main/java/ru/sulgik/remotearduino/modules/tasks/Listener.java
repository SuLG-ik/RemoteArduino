package ru.sulgik.remotearduino.modules.tasks;

import androidx.annotation.NonNull;

public interface Listener <T> {
    void onListen(@NonNull T data);
}
