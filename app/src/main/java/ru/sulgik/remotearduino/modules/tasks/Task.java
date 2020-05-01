package ru.sulgik.remotearduino.modules.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import java.util.concurrent.Executor;

public interface Task <T> {

    boolean isComplete();
    boolean isSuccess();
    boolean isFailed();

    @Nullable T getResult();
    @Nullable Exception getException();

    @Deprecated
    @NonNull Task<T> addOnCompleteListener(@NonNull Listener<Task<T>> listener);
    @NonNull Task<T> addOnCompleteListener(@NonNull LifecycleOwner owner, @NonNull Listener<Task<T>> listener);
    @NonNull Task<T> addOnCompleteListener(@NonNull Executor executor, @NonNull Listener<Task<T>> listener);

    @Deprecated
    @NonNull Task<T> addOnSuccessListener(@NonNull Listener<T> listener);
    @NonNull Task<T> addOnSuccessListener(@NonNull LifecycleOwner owner,@NonNull Listener<T>listener);
    @NonNull Task<T> addOnSuccessListener(@NonNull Executor executor, @NonNull Listener<T> listener);

    @Deprecated
    @NonNull Task<T> addOnFailedListener(@NonNull Listener<Exception> listener);
    @NonNull Task<T> addOnFailedListener(@NonNull LifecycleOwner owner, @NonNull Listener<Exception> listener);
    @NonNull Task<T> addOnFailedListener(@NonNull Executor executor, @NonNull Listener<Exception> listener);

}
