package jmapps.raqaiqquran.ui.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.raqaiqquran.R

class AboutUsBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetStyleFull

    private lateinit var rootAboutUs: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootAboutUs = inflater.inflate(R.layout.bottom_sheet_about_us, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return rootAboutUs
    }

    companion object {
        const val aboutUsTag = "AboutUsBottomSheet"
    }
}