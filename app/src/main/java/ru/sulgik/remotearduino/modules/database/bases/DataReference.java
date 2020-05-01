package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.NonNull;
import org.checkerframework.checker.nullness.compatqual.NullableType;

import java.util.HashMap;

import ru.sulgik.remotearduino.modules.tasks.Task;

public abstract class DataReference {

    @NonNull
    public abstract String getPath();

    @NonNull
    public abstract CatalogReference getParent();

    @NonNull
    public abstract Task<Void> set(@NonNull HashMap<@NullableType String, @NullableType Object> data);

    @NonNull
    public abstract Task<Void> update(@NonNull HashMap<@NullableType String, @NullableType Object> data);

    @NonNull
    public abstract Task<Void> delete();

}
