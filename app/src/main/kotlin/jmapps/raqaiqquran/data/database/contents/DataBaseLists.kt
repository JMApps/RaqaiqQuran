package jmapps.raqaiqquran.data.database.contents

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import jmapps.raqaiqquran.data.database.openhelper.DatabaseOpenHelper
import jmapps.raqaiqquran.ui.data.ContentList

class DataBaseLists(private val context: Context?) {

    private lateinit var database : SQLiteDatabase

    val getChapterList: MutableList<ContentList>
        @SuppressLint("Recycle")
        get() {

            database = DatabaseOpenHelper(context).readableDatabase

            val cursor: Cursor = database.query(
                "Main_content_table",
                null,
                null,
                null,
                null,
                null,
                null)

            val chapterList = ArrayList<ContentList>()

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val chapters = ContentList(
                        cursor.getInt(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("ChapterTitle")),
                        cursor.getString(cursor.getColumnIndex("ChapterContent")))
                    chapterList.add(chapters)
                    cursor.moveToNext()
                    if (cursor.isClosed) {
                        cursor.close()
                    }
                }
            }
            return chapterList
        }
}