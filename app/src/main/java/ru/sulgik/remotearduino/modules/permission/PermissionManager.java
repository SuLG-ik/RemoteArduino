package ru.sulgik.remotearduino.modules.permission;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.jetbrains.annotations.NotNull;

import ru.sulgik.remotearduino.modules.tasks.Listener;
import ru.sulgik.remotearduino.modules.tasks.Task;

public interface PermissionManager {

    @NotNull PermissionManager with(@NotNull FragmentActivity activity, @NotNull MaterialPermission[] permissions, @NotNull Listener<MaterialPermission[]> listener);
    @NotNull Task<MaterialPermission[]> request(@NotNull FragmentActivity activity, @NotNull MaterialPermission[] permissions);

    @NotNull PermissionManager with(@NotNull Fragment fragment, @NotNull MaterialPermission[] permissions, @NotNull Listener<MaterialPermission[]> listener);
    @NotNull Task<MaterialPermission[]> request(@NotNull Fragment fragment, @NotNull MaterialPermission[] permissions);

}
