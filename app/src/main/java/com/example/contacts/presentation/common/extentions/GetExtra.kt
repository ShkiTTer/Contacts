package com.example.contacts.presentation.common.extentions

import android.app.Activity

inline fun <reified T> Activity.getExtra(key: String): T?{
    return intent.extras?.get(key) as? T
}