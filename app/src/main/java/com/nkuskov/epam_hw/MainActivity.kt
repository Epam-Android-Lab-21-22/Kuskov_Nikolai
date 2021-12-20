package com.nkuskov.epam_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.google.android.material.badge.BadgeDrawable
import com.nkuskov.epam_hw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentCallbacksHandler {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val redPageData = NavigationBarStateData(
        RED_PAGE_BACK_STACK,
        RED_PAGE_FRAGMENT_TAG,
        R.color.red,
        R.string.red
    )

    private val bluePageData = NavigationBarStateData(
        BLUE_PAGE_BACK_STACK,
        BLUE_PAGE_FRAGMENT_TAG,
        R.color.blue,
        R.string.blue
    )

    private val greenPageData = NavigationBarStateData(
        GREEN_PAGE_BACK_STACK,
        GREEN_PAGE_FRAGMENT_TAG,
        R.color.green,
        R.string.green
    )

    private val pageDataMap = mapOf(
        NavigationBarState.RED to redPageData,
        NavigationBarState.BLUE to bluePageData,
        NavigationBarState.GREEN to greenPageData
    )

    private var currentState = NavigationBarState.RED

    private var currentPageCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            createFragmentBackStacks()
            setUpDefaultUI()
        }

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            supportFragmentManager.saveBackStack(getPageData(currentState).backStackKey)
            when (item.itemId) {
                R.id.page_red -> {
                    setUpNavigationBarState(NavigationBarState.RED)
                }
                R.id.page_blue -> {
                    setUpNavigationBarState(NavigationBarState.BLUE)
                }
                R.id.page_green -> {
                    setUpNavigationBarState(NavigationBarState.GREEN)
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (currentPageCount-- == 0) {
            finish()
        }
        getBadge(currentState).setUpCount(currentPageCount)
    }

    override fun onFragmentClicked() {
        currentPageCount++
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container,
                MainFragment::class.java,
                bundleOf(
                    KEY_FRAGMENT_NUMBER to currentPageCount,
                    KEY_FRAGMENT_COLOR_ID to getPageData(currentState).colorResourceID
                )
            )
            addToBackStack(getPageData(currentState).backStackKey)
        }
        getBadge(currentState).setUpCount(currentPageCount)
    }

    private fun setUpDefaultUI() {
        getBadge(NavigationBarState.RED).setUpCount(0)
        getBadge(NavigationBarState.BLUE).setUpCount(0)
        getBadge(NavigationBarState.GREEN).setUpCount(0)
        binding.bottomNavigationBar.selectedItemId = R.id.page_red
        setUpNavigationBarState(NavigationBarState.RED)
    }

    private fun createFragmentBackStacks() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container, MainFragment::class.java, bundleOf(
                    KEY_FRAGMENT_NUMBER to 0,
                    KEY_FRAGMENT_COLOR_ID to redPageData.colorResourceID
                ),
                redPageData.fragmentTag
            )
            addToBackStack(redPageData.backStackKey)
        }
        supportFragmentManager.saveBackStack(redPageData.backStackKey)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container, MainFragment::class.java, bundleOf(
                    KEY_FRAGMENT_NUMBER to 0,
                    KEY_FRAGMENT_COLOR_ID to bluePageData.colorResourceID
                ),
                bluePageData.fragmentTag
            )
            addToBackStack(bluePageData.backStackKey)
        }
        supportFragmentManager.saveBackStack(bluePageData.backStackKey)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.fragment_container, MainFragment::class.java, bundleOf(
                    KEY_FRAGMENT_NUMBER to 0,
                    KEY_FRAGMENT_COLOR_ID to greenPageData.colorResourceID
                ),
                greenPageData.fragmentTag
            )
            addToBackStack(greenPageData.backStackKey)
        }
        supportFragmentManager.saveBackStack(greenPageData.backStackKey)
    }

    private fun setUpNavigationBarState(state: NavigationBarState) {
        supportFragmentManager.restoreBackStack(getPageData(state).backStackKey)
        currentState = state
        currentPageCount = getBadge(currentState).number
        supportActionBar?.title =
            getString(R.string.action_bar_placeholder, getString(getPageData(currentState).nameResourceID))
    }

    private fun getBadge(state: NavigationBarState): BadgeDrawable {
        return when (state) {
            NavigationBarState.RED -> binding.bottomNavigationBar.getOrCreateBadge(R.id.page_red)
            NavigationBarState.BLUE -> binding.bottomNavigationBar.getOrCreateBadge(R.id.page_blue)
            NavigationBarState.GREEN -> binding.bottomNavigationBar.getOrCreateBadge(R.id.page_green)
        }
    }

    private fun getPageData(state: NavigationBarState) : NavigationBarStateData {
        return pageDataMap.getOrDefault(state, redPageData)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_RED_COUNT, getBadge(NavigationBarState.RED).number)
        outState.putInt(KEY_BLUE_COUNT, getBadge(NavigationBarState.BLUE).number)
        outState.putInt(KEY_GREEN_COUNT, getBadge(NavigationBarState.GREEN).number)
        outState.putInt(KEY_CURRENT_STATE, currentState.ordinal)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        getBadge(NavigationBarState.RED).setUpCount(savedInstanceState.getInt(KEY_RED_COUNT, 0))
        getBadge(NavigationBarState.BLUE).setUpCount(savedInstanceState.getInt(KEY_BLUE_COUNT, 0))
        getBadge(NavigationBarState.GREEN).setUpCount(savedInstanceState.getInt(KEY_GREEN_COUNT, 0))
        currentState = NavigationBarState.values().first { it.ordinal == savedInstanceState.getInt(KEY_CURRENT_STATE, 0) }
        setUpNavigationBarState(currentState)
    }

    private fun BadgeDrawable.setUpCount(number: Int) {
        this.number = number
        this.isVisible = number > 0
    }

    companion object {
        const val KEY_FRAGMENT_NUMBER = "KEY_FRAGMENT_NUMBER"
        const val KEY_FRAGMENT_COLOR_ID = "KEY_FRAGMENT_COLOR_ID"
        const val KEY_CURRENT_STATE = "KEY_CURRENT_STATE"
        const val RED_PAGE_BACK_STACK = "RED_PAGE_BACK_STACK"
        const val RED_PAGE_FRAGMENT_TAG = "RED_PAGE_FRAGMENT_TAG"
        const val KEY_RED_COUNT = "KEY_RED_COUNT"
        const val BLUE_PAGE_BACK_STACK = "BLUE_PAGE_BACK_STACK"
        const val BLUE_PAGE_FRAGMENT_TAG = "BLUE_PAGE_FRAGMENT_TAG"
        const val KEY_BLUE_COUNT = "KEY_BLUE_COUNT"
        const val GREEN_PAGE_BACK_STACK = "GREEN_PAGE_BACK_STACK"
        const val GREEN_PAGE_FRAGMENT_TAG = "GREEN_PAGE_FRAGMENT_TAG"
        const val KEY_GREEN_COUNT = "KEY_GREEN_COUNT"

        enum class NavigationBarState {
            RED,
            BLUE,
            GREEN
        }
    }

    data class NavigationBarStateData(
        val backStackKey: String,
        val fragmentTag: String,
        val colorResourceID: Int,
        val nameResourceID: Int
    )
}