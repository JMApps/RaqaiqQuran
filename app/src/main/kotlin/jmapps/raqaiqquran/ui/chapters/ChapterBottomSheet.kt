package jmapps.raqaiqquran.ui.chapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.data.database.contents.DataBaseLists
import jmapps.raqaiqquran.databinding.BottomSheetChaptersBinding
import jmapps.raqaiqquran.ui.data.ContentList

class ChapterBottomSheet : BottomSheetDialogFragment(), ChapterListAdapter.OnItemChapterClick {

    override fun getTheme(): Int = R.style.BottomSheetStyleFull

    private lateinit var binding: BottomSheetChaptersBinding

    private lateinit var contentList: MutableList<ContentList>
    private lateinit var chapterListAdapter: ChapterListAdapter

    private lateinit var setCurrentChapter: SetCurrentChapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_chapters, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        contentList = DataBaseLists(requireContext()).getChapterList

        val verticalLayout = LinearLayoutManager(context)
        binding.rvChapterList.layoutManager = verticalLayout

        chapterListAdapter = ChapterListAdapter(requireContext(), contentList, this)
        binding.rvChapterList.adapter = chapterListAdapter

        return binding.root
    }

    companion object {
        const val chaptersTag = "ChaptersBottomSheet"
    }

    override fun onItemClick(chapterId: Int) {
        setCurrentChapter.setCurrentChapter(chapterId)
        dialog?.dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SetCurrentChapter) {
            setCurrentChapter = context
        } else {
            throw RuntimeException("$context must implement SetCurrentChapter")
        }
    }

    interface SetCurrentChapter {
        fun setCurrentChapter(chapterId: Int)
    }
}