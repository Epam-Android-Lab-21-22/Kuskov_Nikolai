package com.nkuskov.epam_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.google.android.material.badge.BadgeDrawable
import com.nkuskov.epam_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentCallbacksHandler {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private fun getBadge() =
        binding.bottomNavigationBar.getOrCreateBadge(binding.bottomNavigationBar.selectedItemId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            createFragmentBackStacks()
            supportFragmentManager.restoreBackStack(binding.bottomNavigationBar.selectedItemId.toString())
            setUpActionBarTitle(binding.bottomNavigationBar.selectedItemId)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            getBadge().setUpCount(supportFragmentManager.backStackEntryCount - 1)
        }

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            if (item.itemId != binding.bottomNavigationBar.selectedItemId)
                supportFragmentManager.saveBackStack(binding.bottomNavigationBar.selectedItemId.toString())
            supportFragmentManager.restoreBackStack(item.itemId.toString())
            setUpActionBarTitle(item.itemId)
            true
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1 ) {
            finish()
        }
        super.onBackPressed()
        getBadge().setUpCount(supportFragmentManager.backStackEntryCount - 1)
    }

    override fun onFragmentClicked(state: FragmentState) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container,
                MainFragment::class.java,
                bundleOf(MainFragment.KEY_CURRENT_STATE to state.ordinal
                )
            )
            addToBackStack(binding.bottomNavigationBar.selectedItemId.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpActionBarTitle(binding.bottomNavigationBar.selectedItemId)
    }

    private fun createFragmentBackStacks() {
        initBackStack(FragmentState.RED)
        initBackStack(FragmentState.BLUE)
        initBackStack(FragmentState.GREEN)
    }

    private fun initBackStack(state: FragmentState) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container,
                MainFragment::class.java,
                bundleOf(MainFragment.KEY_CURRENT_STATE to state.ordinal
                )
            )
            addToBackStack(getStackIDFromState(state).toString())
        }
        supportFragmentManager.saveBackStack(getStackIDFromState(state).toString())
    }

    private fun setUpActionBarTitle(selectedPageID: Int) {
        supportActionBar?.title =
            getString(
                R.string.action_bar_placeholder,
                getString(getStackNameFromSelectedPage(selectedPageID))
            )
    }

    private fun BadgeDrawable.setUpCount(number: Int) {
        this.number = number
        this.isVisible = number > 0
    }

    companion object {
        private fun getStackIDFromState(state: FragmentState): Int {
            return when (state) {
                FragmentState.RED -> R.id.page_red
                FragmentState.BLUE -> R.id.page_blue
                FragmentState.GREEN -> R.id.page_green
            }
        }

        private fun getStackNameFromSelectedPage(id: Int): Int {
            return when (id) {
                R.id.page_red -> R.string.red
                R.id.page_blue -> R.string.blue
                R.id.page_green -> R.string.green
                else -> R.string.app_name
            }
        }
    }
}