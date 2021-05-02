package com.github.vitkidd.geosearch.feature.presentation.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.vitkidd.geosearch.R
import com.github.vitkidd.geosearch.databinding.FmtPhotoBinding
import com.github.vitkidd.geosearch.feature.presentation.model.PhotoModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhotoDialog : BottomSheetDialogFragment() {

    private val binding by viewBinding(FmtPhotoBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fmt_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoModel: PhotoModel = arguments?.getParcelable(ARG_PHOTO) ?: run {
            dismiss()
            return
        }

        Glide.with(requireContext())
            .load(photoModel.urlMedium)
            .centerCrop()
            .into(binding.imageView)

        binding.titleTextView.isVisible = photoModel.title.isNotEmpty()
        binding.titleValueTextView.isVisible = photoModel.title.isNotEmpty()
        binding.tagsTextView.isVisible = photoModel.tags.isNotEmpty()
        binding.tagsValueTextView.isVisible = photoModel.tags.isNotEmpty()

        binding.titleValueTextView.text = photoModel.title
        binding.tagsValueTextView.text = photoModel.tags
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dlg = super.onCreateDialog(savedInstanceState)

        dlg.setOnShowListener { dialog ->
            val bottomSheet = (dialog as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout?
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return dlg
    }

    companion object {

        private const val ARG_PHOTO = "ARG_PHOTO"

        fun show(manager: FragmentManager?, photoModel: PhotoModel) = manager?.let { fm ->
            PhotoDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PHOTO, photoModel)
                }
            }.show(fm, "PhotoDialog")
        }
    }
}