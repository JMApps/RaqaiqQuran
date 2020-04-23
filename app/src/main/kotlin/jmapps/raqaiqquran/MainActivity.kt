package jmapps.raqaiqquran

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import jmapps.raqaiqquran.databinding.ActivityMainBinding
import jmapps.raqaiqquran.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.vpMainContainer.adapter = sectionsPagerAdapter
        binding.diMainContainer.attachViewPager(binding.vpMainContainer)
        binding.diMainContainer.setDotTintRes(R.color.red)

        binding.appBarMain.addOnOffsetChangedListener(offsetChangedListener)
        binding.fabChapters.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
    }

    private val offsetChangedListener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
        if (verticalOffset < 0) {
            binding.fabChapters.hide()
        } else {
            binding.fabChapters.show()
        }
    }
}