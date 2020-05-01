package ru.sulgik.remotearduino.modules.database.bases;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sulgik.remotearduino.modules.tasks.Task;

public abstract class AuthController {

    public abstract boolean isLoggedOut();

    @Nullable
    public abstract Profile getUser();

    @NonNull
    public abstract Task<Profile> signUp(@NonNull String email, @NonNull String password);

    @NonNull
    public abstract Task<Profile> sighIn(@NonNull String email, @NonNull String password);

    public abstract void signOut();
    

}
