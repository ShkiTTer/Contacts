package com.example.contacts.presentation.common.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore

object IntentUtils {
    fun createImageGalleryIntent(): Intent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    fun createCaptureCameraIntent(packageManager: PackageManager): Intent? {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)
        }
    }
}