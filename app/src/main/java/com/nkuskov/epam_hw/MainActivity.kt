package com.nkuskov.epam_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.google.android.material.badge.BadgeDrawable
import com.nkuskov.epam_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentCallbacksHandler {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private fun getBadge() = binding.bottomNavigationBar.getOrCreateBadge(binding.bottomNavigationBar.selectedItemId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            var bundle = bundleOf(
                "MainFragment.KEY_CURRENT_STATE" to 5
            )
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(
                    R.id.fragment_container,
                    MainFragment::class.java,
                    bundleOf(
                        "MainFragment.KEY_CURRENT_STATE" to 5
                    )
                )
                addToBackStack(binding.bottomNavigationBar.selectedItemId.toString())
            }
//            createFragmentBackStacks()
            supportFragmentManager.restoreBackStack(binding.bottomNavigationBar.selectedItemId.toString())
        }

        supportFragmentManager.addOnBackStackChangedListener {
            getBadge().setUpCount(supportFragmentManager.backStackEntryCount - 1)
        }

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            if(item.itemId != binding.bottomNavigationBar.selectedItemId)
                supportFragmentManager.saveBackStack(item.itemId.toString())
            setUpNavigationBarState(item.itemId)
            true
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        }
        super.onBackPressed()
        getBadge().setUpCount(supportFragmentManager.backStackEntryCount)
    }

    override fun onFragmentClicked(state: FragmentState) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container,
                MainFragment::class.java,
                bundleOf(
                    "MainFragment.KEY_CURRENT_STATE" to state.ordinal
                )
            )
            addToBackStack(binding.bottomNavigationBar.selectedItemId.toString())
        }
    }

    private fun createFragmentBackStacks() {
        initBackStack(FragmentState.RED)
        initBackStack(FragmentState.BLUE)
        initBackStack(FragmentState.GREEN)
    }

    private fun initBackStack(state: FragmentState) {
        val stackID = getStackIDFromState(state)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container, MainFragment::class.java, bundleOf(
                    "sasdasd" to state.ordinal,
                    "sdad" to 20
                )
            )
            addToBackStack(stackID.toString())
        }
        supportFragmentManager.saveBackStack(stackID.toString())
    }

    private fun setUpNavigationBarState(stackID: Int) {
        supportFragmentManager.restoreBackStack(stackID.toString())
    }

    private fun BadgeDrawable.setUpCount(number: Int) {
        this.number = number
        this.isVisible = number > 0
    }

    companion object {
        fun getStackIDFromState(state: FragmentState) : Int {
            return when (state) {
                FragmentState.RED -> R.id.page_red
                FragmentState.BLUE -> R.id.page_blue
                FragmentState.GREEN -> R.id.page_green
            }
        }
    }
}