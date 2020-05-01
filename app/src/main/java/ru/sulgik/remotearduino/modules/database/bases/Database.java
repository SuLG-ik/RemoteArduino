package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public abstract class Database {

    public abstract boolean isRemote();

    @Nullable
    public abstract Profile getUser();

    @NonNull
    public abstract AuthController getAuthController();

    @NotNull
    public abstract StorageController getStorageController();

}
