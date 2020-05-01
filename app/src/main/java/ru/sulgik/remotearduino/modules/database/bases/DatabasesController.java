package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public interface DatabasesController {

    @NotNull
    Database getLocalDatabase();

    @NotNull
    Database getRemoteDatabase();

}
