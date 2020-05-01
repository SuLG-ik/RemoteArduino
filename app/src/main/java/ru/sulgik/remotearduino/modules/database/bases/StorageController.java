package ru.sulgik.remotearduino.modules.database.bases;

import androidx.annotation.NonNull;

public abstract class StorageController {

    @NonNull
    public abstract CatalogReference catalog(String path);

    @NonNull
    public abstract DataReference data(String path);

}
