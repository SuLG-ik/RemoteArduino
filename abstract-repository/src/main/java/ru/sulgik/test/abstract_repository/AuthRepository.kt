package ru.sulgik.test.abstract_repository

import ru.sulgik.test.common.ObservableCase

interface AuthRepository{
    val user : ObservableCase<Profile>
}