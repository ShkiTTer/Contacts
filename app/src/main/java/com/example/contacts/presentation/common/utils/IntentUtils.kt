package com.example.contacts.presentation.common.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore

object IntentUtils {
    fun createImageGalleryIntent(): Intent =
        Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }

    fun createCaptureCameraIntent(packageManager: PackageManager, outputUri: Uri): Intent? {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            takePictureIntent.resolveActivity(packageManager)
        }
    }
}