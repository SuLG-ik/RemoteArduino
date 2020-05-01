package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.Nullable;

public interface UpdateListener<T>{

    void onUpdate(@Nullable T reference, @Nullable Exception e);

}
