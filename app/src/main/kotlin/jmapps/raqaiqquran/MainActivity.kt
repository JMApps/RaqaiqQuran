package jmapps.raqaiqquran

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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

        binding.fabChapters.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
    }
}