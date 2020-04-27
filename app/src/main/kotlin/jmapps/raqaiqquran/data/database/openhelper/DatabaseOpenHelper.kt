package jmapps.raqaiqquran.data.database.openhelper

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

private var dbVersion: Int = 1

class DatabaseOpenHelper(context: Context?) :
    SQLiteAssetHelper(context, "RaqaiqDataBase.db", null, dbVersion) {

    init {
        setForcedUpgrade()
    }
}