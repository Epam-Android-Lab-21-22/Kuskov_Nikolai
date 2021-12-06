package com.nkuskov.epam_hw

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.helper.widget.Carousel

class MainActivity : AppCompatActivity() {
    private val carousel: Carousel by lazy { findViewById(R.id.carousel) }
    private val goNextButton: Button by lazy { findViewById(R.id.goNextButton) }
    private val answerEditText: EditText by lazy { findViewById(R.id.answer_edit_text) }
    private val symbolsCountTextView: TextView by lazy { findViewById(R.id.edit_text_count) }
    private val buttonsCountTextView: TextView by lazy { findViewById(R.id.button_clicked_count) }
    private val categoryNameTextView: TextView by lazy { findViewById(R.id.category_text_view) }
    private val questionTextView: TextView by lazy { findViewById(R.id.question_text_view) }

    private var symbolsCount = 0
    private var buttonsCount = 0

    private var selctedIndex = 0;

    private val carouselAnimationDelay = 200

    private val SELECTEDINDEX = "SELECTED_INDEX"
    private val ANSWERKEY = "ANSWER_KEY"
    private val SYMBOLSCOUNTKEY = "SYMBOLS_COUNT_KEY"
    private val BUTTONSCOUNTKEY = "BUTTONS_COUNT_KEY"

    private val categoryInfoList = listOf(
        CategoryInfo(
            R.drawable.ic_abc_108,
            R.string.title_abc,
            R.string.question_abc_1,
            R.string.answer_abc_1
        ),
        CategoryInfo(
            R.drawable.ic_art_108,
            R.string.title_art,
            R.string.question_art_1,
            R.string.answer_art_1
        ),
        CategoryInfo(
            R.drawable.ic_biology_108,
            R.string.title_biology,
            R.string.question_biology_1,
            R.string.answer_biology_1
        ),
        CategoryInfo(
            R.drawable.ic_geography_108,
            R.string.title_geography,
            R.string.question_geography_1,
            R.string.answer_geography_1
        ),
        CategoryInfo(
            R.drawable.ic_science_108,
            R.string.title_science,
            R.string.question_science_1,
            R.string.answer_science_1
        ),
        CategoryInfo(
            R.drawable.ic_mathematic_108,
            R.string.title_mathematica,
            R.string.question_mathematica_1,
            R.string.answer_mathematica_1
        ),
        CategoryInfo(
            R.drawable.ic_music_108,
            R.string.title_music,
            R.string.question_music_1,
            R.string.answer_music_1
        ),
        CategoryInfo(
            R.drawable.ic_physics_108,
            R.string.title_physics,
            R.string.question_physics_1,
            R.string.answer_physics_1
        ),
        CategoryInfo(
            R.drawable.ic_sports_108,
            R.string.title_sport,
            R.string.question_sports_1,
            R.string.answer_sports_1
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setUpCarousel()
        updateUI()

        goNextButton.setOnClickListener {
            selctedIndex = (IntArray(categoryInfoList.count()) { it }).random()
            buttonsCount++
            carousel.transitionToIndex(selctedIndex, carouselAnimationDelay)
        }

        answerEditText.setOnEditorActionListener { _, actionId, code ->
            if (actionId == EditorInfo.IME_ACTION_DONE || code.keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(answerEditText)
                Toast.makeText(
                    this,
                    getString(
                        if (getString(categoryInfoList[selctedIndex].answerResId).equals(
                                answerEditText.text.toString(),
                                ignoreCase = true
                            )
                        ) R.string.right_answer else R.string.wrong_answer
                    ),
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        answerEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                symbolsCount++
                updateUI()
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        hideKeyboard(currentFocus ?: View(this))
        return super.dispatchTouchEvent(event)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun updateUI() {
        symbolsCountTextView.text =
            "${getString(R.string.entered_symbols_default_value)} + $symbolsCount"
        buttonsCountTextView.text =
            "${getString(R.string.button_clicked_default_calue)} + $buttonsCount"
        questionTextView.text = getString(categoryInfoList[selctedIndex].questionResId)
        categoryNameTextView.text = getString(categoryInfoList[selctedIndex].nameResId)
    }

    private fun setUpCarousel() {
        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                return categoryInfoList.count()
            }

            override fun populate(view: View?, index: Int) {
                if (view is ImageView) {
                    view.setImageResource(categoryInfoList[index].iconResId)
                }
            }

            override fun onNewItem(index: Int) {
                selctedIndex = index
                updateUI()
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(SELECTEDINDEX, selctedIndex)
            putString(ANSWERKEY, answerEditText.text.toString())
            putInt(SYMBOLSCOUNTKEY, symbolsCount)
            putInt(BUTTONSCOUNTKEY, buttonsCount)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            selctedIndex = getInt(SELECTEDINDEX)
            answerEditText.setText(getString(ANSWERKEY))
            symbolsCount = getInt(SYMBOLSCOUNTKEY)
            buttonsCount = getInt(BUTTONSCOUNTKEY)
        }
        updateUI()
    }

    data class CategoryInfo(
        val iconResId: Int,
        val nameResId: Int,
        val questionResId: Int,
        val answerResId: Int
    )
}