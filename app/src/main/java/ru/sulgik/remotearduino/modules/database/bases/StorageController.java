package ru.sulgik.remotearduino.modules.database.bases;

import androidx.lifecycle.LiveData;

import ru.sulgik.remotearduino.modules.database.RemoteDevice;

public abstract class StorageController {

    public abstract LiveData<RemoteDevice> getAllDevices();

}
