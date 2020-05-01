package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class Database {

    public abstract boolean isRemote();

    @Nullable
    public abstract Profile getUser();

    @NonNull
    public abstract AuthController getAuthController();

    @NonNull
    public abstract StorageController getStorageController();

}
