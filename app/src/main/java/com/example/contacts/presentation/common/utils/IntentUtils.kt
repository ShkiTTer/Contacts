package com.example.contacts.presentation.common.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore

object IntentUtils {
    fun createImageGalleryIntent(): Intent =
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    fun createCaptureCameraIntent(packageManager: PackageManager): Intent? {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)
        }
    }
}