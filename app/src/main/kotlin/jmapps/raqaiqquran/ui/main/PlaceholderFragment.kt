package jmapps.raqaiqquran.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.data.database.contents.DataBaseLists
import jmapps.raqaiqquran.databinding.FragmentMainBinding
import jmapps.raqaiqquran.ui.data.ContentList

class PlaceholderFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var sectionNumber: Int? = null

    private lateinit var contentList: MutableList<ContentList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)

        contentList = DataBaseLists(context).getChapterList
        binding.tvChapterContent.text = contentList[sectionNumber!! - 1].strChapterContent

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
}