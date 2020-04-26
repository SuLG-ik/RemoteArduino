package ru.sulgik.remotearduino.tasks;

public interface Listener <T> {
    void onListen(T data);
}
