package jmapps.raqaiqquran.ui.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import jmapps.raqaiqquran.R
import jmapps.raqaiqquran.data.database.contents.DataBaseLists
import jmapps.raqaiqquran.data.database.openhelper.DatabaseOpenHelper
import jmapps.raqaiqquran.databinding.ActivityMainBinding
import jmapps.raqaiqquran.mvp.other.OtherContract
import jmapps.raqaiqquran.mvp.other.OtherPresenterImpl
import jmapps.raqaiqquran.ui.aboutus.AboutUsBottomSheet
import jmapps.raqaiqquran.ui.data.ContentList
import jmapps.raqaiqquran.ui.main.SectionsPagerAdapter
import jmapps.raqaiqquran.ui.settings.SettingsBottomSheet

class MainActivity : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener,
    OtherContract.OtherView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: SQLiteDatabase
    private lateinit var contentList: MutableList<ContentList>

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var otherPresenter: OtherPresenterImpl

    private var valNightMode: Boolean = false
    private lateinit var nightModeItem: MenuItem

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        database = DatabaseOpenHelper(this).writableDatabase
        contentList = DataBaseLists(this).getChapterList

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        otherPresenter = OtherPresenterImpl(this, this)

        valNightMode = preferences.getBoolean("key_night_mode", false)
        isNightMode(valNightMode)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.vpMainContainer.adapter = sectionsPagerAdapter
        binding.diMainContainer.attachViewPager(binding.vpMainContainer)
        binding.diMainContainer.setDotTintRes(R.color.dotsColor)
        binding.vpMainContainer.addOnPageChangeListener(this)

        val lastPosition = preferences.getInt("key_last_position", 0)
        binding.tvChapterTitle.text = contentList[lastPosition].strChapterTitle

        binding.appBarMain.addOnOffsetChangedListener(offsetChangedListener)
        binding.fabChapters.setOnClickListener(this)

        loadLastPosition(lastPosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        nightModeItem = menu.findItem(R.id.action_night_mode)
        nightModeItem.isChecked = valNightMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_night_mode -> {
                otherPresenter.setNightMode(!nightModeItem.isChecked)
            }

            R.id.action_settings -> {
                otherPresenter.getSettings()
            }

            R.id.action_about_us -> {
                otherPresenter.getAboutUs()
            }

            R.id.action_rate -> {
                otherPresenter.rateApp()
            }

            R.id.action_share -> {
                otherPresenter.shareLink()
            }
        }
        return super.onOptionsItemSelected(item)
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

    override fun showSettings() {
        val settings = SettingsBottomSheet()
        settings.show(supportFragmentManager, SettingsBottomSheet.settingsUsTag)
    }

    override fun showAboutUs() {
        val aboutUs = AboutUsBottomSheet()
        aboutUs.show(supportFragmentManager, AboutUsBottomSheet.aboutUsTag)
    }

    override fun getNightMode(state: Boolean) {
        isNightMode(state)
        editor.putBoolean("key_night_mode", state).apply()
    }

    override fun isNightMode(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}