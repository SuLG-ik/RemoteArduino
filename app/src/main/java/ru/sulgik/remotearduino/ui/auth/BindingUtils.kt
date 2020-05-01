package ru.sulgik.remotearduino.ui.auth

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.*
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso


@BindingAdapter("app:uri", "app:errorUri")
fun loadPicture(view: ImageView, uri: Uri?, error: Drawable) {
    if (uri != null)
        Picasso.get().load(uri).error(error).into(view)
    else
        view.setImageDrawable(error)
}

@BindingAdapter("app:userName", "app:errorUserNameMessage")
fun showUserName(view: TextView, user: FirebaseUser?, errorText: String) {
    if (user == null)
        view.text = errorText
    else
        view.text = user.displayName ?: user.email
}