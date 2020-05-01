package ru.sulgik.remotearduino.modules.database.bases;

import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import org.jetbrains.annotations.NotNull;

import ru.sulgik.remotearduino.modules.tasks.Task;

public abstract class AuthController {

    public abstract boolean isLoggedOut();

    @Nullable
    public abstract Profile getUser();

    @NotNull
    public abstract Task<Profile> signUp(@NotNull String email, @NotNull String password);

    @NotNull
    public abstract Task<Profile> sighIn(@NotNull String email, @NotNull String password);

    public abstract void signOut();
    

}
