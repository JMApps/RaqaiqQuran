package jmapps.raqaiqquran.ui.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.data.database.contents.DataBaseLists
import jmapps.raqaiqquran.data.database.openhelper.DatabaseOpenHelper
import jmapps.raqaiqquran.databinding.ActivityMainBinding
import jmapps.raqaiqquran.ui.data.ContentList
import jmapps.raqaiqquran.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: SQLiteDatabase
    private lateinit var contentList: MutableList<ContentList>

    private lateinit var presenter: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        database = DatabaseOpenHelper(this).writableDatabase
        contentList = DataBaseLists(this).getChapterList

        presenter = PreferenceManager.getDefaultSharedPreferences(this)
        editor = presenter.edit()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.vpMainContainer.adapter = sectionsPagerAdapter
        binding.diMainContainer.attachViewPager(binding.vpMainContainer)
        binding.diMainContainer.setDotTintRes(R.color.red)
        binding.vpMainContainer.addOnPageChangeListener(this)

        val lastPosition = presenter.getInt("key_last_position", 0)
        binding.tvChapterTitle.text = contentList[lastPosition].strChapterTitle

        binding.appBarMain.addOnOffsetChangedListener(offsetChangedListener)
        binding.fabChapters.setOnClickListener(this)

        loadLastPosition(lastPosition)
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        binding.tvChapterTitle.text = contentList[position].strChapterTitle
    }

    override fun onClick(v: View?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        saveLastPosition()
    }

    private val offsetChangedListener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
        if (verticalOffset < 0) {
            binding.fabChapters.hide()
        } else {
            binding.fabChapters.show()
        }
    }

    private fun saveLastPosition() {
        editor.putInt("key_last_position", binding.vpMainContainer.currentItem).apply()
    }

    private fun loadLastPosition(lastPosition: Int) {
        binding.vpMainContainer.currentItem = lastPosition
    }
}