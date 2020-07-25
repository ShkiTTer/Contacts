package com.example.contacts.presentation.common.extentions

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

fun Activity.requirePermission(
    permission: String,
    permissionGranted: () -> Unit = {},
    permissionDeny: () -> Unit = {}
) {
    Dexter.withActivity(this).withPermission(permission).withListener(object : PermissionListener {
        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
            permissionGranted()
        }

        override fun onPermissionRationaleShouldBeShown(
            permission: PermissionRequest?,
            token: PermissionToken?
        ) {
            token?.continuePermissionRequest()
        }

        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
            permissionDeny()
        }
    }).check()
}