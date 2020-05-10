package jmapps.raqaiqquran.ui.chapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.ui.data.ContentList

class ChapterListAdapter(
    context: Context,
    private var bookContentList: MutableList<ContentList>,
    private val onItemChapterClick: OnItemChapterClick) : RecyclerView.Adapter<ChapterHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface OnItemChapterClick {
        fun onItemClick(chapterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        val contentView = inflater.inflate(R.layout.item_chapter, parent, false)
        return ChapterHolder(contentView)
    }

    override fun getItemCount(): Int {
        return bookContentList.size
    }

    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        val current = bookContentList[position]

        holder.tvChapterId.text = current.idContent.toString()
        holder.tvChapterName.text = current.strChapterTitle

        holder.findItemClick(onItemChapterClick, current.idContent)
    }
}