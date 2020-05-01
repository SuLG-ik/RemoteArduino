package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.NonNull;
import org.checkerframework.checker.nullness.compatqual.NonNullType;
import org.checkerframework.checker.nullness.compatqual.NullableType;
import java.util.HashMap;
import ru.sulgik.remotearduino.modules.tasks.Task;

public abstract class CatalogReference {

    @NonNull
    public abstract String getId();

    @NonNull
    public abstract DataReference data();

    @NonNull
    public abstract DataReference data(@NonNull String path);

    @NonNull
    public abstract String getPath();

    @NonNull
    public abstract Task<DataReference> insert(@NonNull HashMap<@NonNullType String, @NullableType Object> data);

}
