package ru.sulgik.test.authentication

import android.content.Context
import android.text.InputType
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object InputView {

    fun getEmailInput(context : Context, hint: String = context.getString(R.string.email), errorEnabled: Boolean = true): TextInputLayout {
        return generateEditTextLayout(context, hint, errorEnabled = errorEnabled)
    }

    fun getPasswordInput(context: Context, hint : String = context.getString(R.string.password), errorEnabled: Boolean = true): TextInputLayout {
        return generateEditTextLayout(context, hint, InputType.TYPE_TEXT_VARIATION_PASSWORD, hideText = true, errorEnabled = errorEnabled)
    }

    fun generateEditTextView(context: Context, type: Int): TextInputEditText {
        return TextInputEditText(context).apply {
            inputType = type
        }
    }

    fun generateEditTextLayout(
        context: Context,
        hint: String,
        type: Int = InputType.TYPE_CLASS_TEXT,
        errorEnabled: Boolean = false,
        hideText: Boolean = false,
        style: Int = R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
    ): TextInputLayout {
        return TextInputLayout(context, null, style).apply {
            addView(generateEditTextView(context, type))
            this.hint = hint

            if (hideText)
                endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

            isErrorEnabled = errorEnabled
        }
    }

}