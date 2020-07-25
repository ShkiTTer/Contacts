package com.example.contacts.presentation.editcontact

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contacts.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_select_photo.*

class SelectPhotoBottomSheet : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "SelectPhotoBottomSheet"

        fun newInstance() = SelectPhotoBottomSheet()
    }

    private lateinit var callback: OnSelectPhotoListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = context as OnSelectPhotoListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_select_photo, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        tvCamera.setOnClickListener {
            callback.takeByCamera()
        }

        tvGallery.setOnClickListener {
            callback.takeByGallery()
        }
    }

    interface OnSelectPhotoListener {
        fun takeByCamera()
        fun takeByGallery()
    }
}