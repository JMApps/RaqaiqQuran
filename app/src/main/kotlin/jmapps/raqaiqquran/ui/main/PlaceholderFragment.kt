package jmapps.raqaiqquran.ui.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.data.database.contents.DataBaseLists
import jmapps.raqaiqquran.databinding.FragmentMainBinding
import jmapps.raqaiqquran.mvp.read.ScrollPresenterImpl
import jmapps.raqaiqquran.ui.data.ContentList

class PlaceholderFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var binding: FragmentMainBinding
    private var sectionNumber: Int? = null

    private lateinit var scrollPresenterImpl: ScrollPresenterImpl
    private lateinit var contentList: MutableList<ContentList>

    private var textSizeValues = (16..30).toList().filter { it % 2 == 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences.edit()
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)

        textSize()

        scrollPresenterImpl = ScrollPresenterImpl(
            sectionNumber!!,
            binding.nsMainContainer,
            binding.pbReadProgress
        )
        scrollPresenterImpl.scrollCount()
        scrollPresenterImpl.loadLastCount(preferences)

        contentList = DataBaseLists(context).getChapterList
        binding.tvChapterContent.text = Html.fromHtml(contentList[sectionNumber!! - 1].strChapterContent)

        return binding.root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        textSize()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        scrollPresenterImpl.saveLastCount(editor)
    }

    private fun textSize() {
        val lastProgressTextSize = preferences.getInt("key_text_size_progress", 1)
        binding.tvChapterContent.textSize = textSizeValues[lastProgressTextSize].toFloat()
    }
}