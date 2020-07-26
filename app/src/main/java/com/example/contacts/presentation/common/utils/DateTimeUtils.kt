package com.example.contacts.presentation.common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    private const val TIME_STAMP_PATTERN = "yyyyMMdd_HHmmss"

    fun getTimeStamp() = SimpleDateFormat(TIME_STAMP_PATTERN).format(Date())
}