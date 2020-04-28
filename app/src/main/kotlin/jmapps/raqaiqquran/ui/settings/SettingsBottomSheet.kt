package jmapps.raqaiqquran.ui.settings

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.databinding.BottomSheetSettingsBinding

class SettingsBottomSheet : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener {

    private lateinit var settingBinding: BottomSheetSettingsBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var textSizeValues = (16..30).toList().filter { it % 2 == 0 }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        settingBinding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_settings, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()

        val lastProgressTextSize = preferences.getInt("key_text_size_progress", 1)
        settingBinding.sbTextSize.progress = lastProgressTextSize
        settingBinding.tvTextSizeCount.text = textSizeValues[lastProgressTextSize].toString()

        settingBinding.sbTextSize.setOnSeekBarChangeListener(this)

        return settingBinding.root
    }

    companion object {
        const val settingsUsTag = "SettingsBottomSheet"
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        settingBinding.tvTextSizeCount.text = textSizeValues[progress].toString()
        editor.putInt("key_text_size_progress", progress).apply()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}