package ru.sulgik.remotearduino.modules.database.bases;

import java.util.HashMap;

public abstract class DataSnapshot {

    public abstract HashMap<String, Object> getData();

    public abstract String getString(String field);

    public abstract Long getLong(String field);

    public abstract Long getDate(String field);

    public abstract <T> T get(String field);

}
