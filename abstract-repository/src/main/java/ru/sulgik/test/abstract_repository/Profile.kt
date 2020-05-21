package ru.sulgik.test.abstract_repository

data class Profile(
    val isAnonymous : Boolean,
    val email : String?,
    val name : String?,
    val pictureUri : String?
)