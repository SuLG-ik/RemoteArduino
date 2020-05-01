package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.NonNull;

public interface DatabasesController {

    @NonNull
    Database getLocalDatabase();

    @NonNull
    Database getRemoteDatabase();

}
