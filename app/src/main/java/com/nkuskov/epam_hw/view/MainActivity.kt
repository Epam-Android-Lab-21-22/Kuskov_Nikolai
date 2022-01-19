package com.nkuskov.epam_hw.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.nkuskov.epam_hw.R
import com.nkuskov.epam_hw.databinding.ActivityMainBinding
import com.nkuskov.epam_hw.model.GridItemsModel
import com.nkuskov.epam_hw.model.VerticalItemsModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            createFragment(VerticalRecyclerViewFragment::class.java)
        }

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.first_page -> createFragment(VerticalRecyclerViewFragment::class.java)
                R.id.second_page -> createFragment(GridRecyclerViewFragment::class.java)
            }
            true
        }
    }

    private fun createFragment(fragmentClass: Class<out Fragment>) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_container,
                fragmentClass,
                null
            )
        }
    }
}