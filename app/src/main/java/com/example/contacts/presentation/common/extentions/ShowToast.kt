package com.example.contacts.presentation.common.extentions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.showShortToast(@StringRes message: Int) =
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

fun Activity.showLongToast(@StringRes message: Int) =
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()