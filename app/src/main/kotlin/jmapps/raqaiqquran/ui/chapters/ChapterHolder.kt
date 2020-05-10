package jmapps.raqaiqquran.ui.chapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jmapps.raqaiqquran.R

class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvChapterId: TextView = itemView.findViewById(R.id.tvChapterId)
    val tvChapterName: TextView = itemView.findViewById(R.id.tvChapterName)

    fun findItemClick(onItemChapterClick: ChapterListAdapter.OnItemChapterClick, chapterId: Int) {
        itemView.setOnClickListener {
            onItemChapterClick.onItemClick(chapterId)
        }
    }
}