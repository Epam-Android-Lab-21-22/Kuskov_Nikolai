package com.nkuskov.epam_hw

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val imbCalculationButton: Button by lazy { findViewById(R.id.calculate_imb_button) }
    private val imbCoefficientTextView: TextView by lazy { findViewById(R.id.imb_coefficient_text_view) }
    private val imbDescriptionTextView: TextView by lazy { findViewById(R.id.imb_description_text_view) }
    private val imbWeightEditTextView: EditText by lazy { findViewById(R.id.weight_edit_text) }
    private val imbHeightEditTextView: EditText by lazy { findViewById(R.id.height_edit_text) }

    private val imbDescriptionTextColorMap: Map<IMBCalculator.IMBType, Int> = mapOf(IMBCalculator.IMBType.PronouncedDeficit to R.color.blue,
                                                                                    IMBCalculator.IMBType.Deficit to R.color.black,
                                                                                    IMBCalculator.IMBType.Normal to R.color.black,
                                                                                    IMBCalculator.IMBType.ExcessWeight to R.color.black,
                                                                                    IMBCalculator.IMBType.FirstDegree to R.color.black,
                                                                                    IMBCalculator.IMBType.SecondDegree to R.color.red,
                                                                                    IMBCalculator.IMBType.ThirdDegree to R.color.red)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imbCalculationButton.setOnClickListener{
            hideKeyboard(currentFocus ?: View(this))
            var height = imbHeightEditTextView.text.toString().toDoubleOrNull()
            var weight = imbWeightEditTextView.text.toString().toDoubleOrNull()
            if(isEnteredValuesValid(height, weight)) {
                IMBCalculator.getIMBData(weight!!, height!!).let { imbData ->
                    imbDescriptionTextView.apply {
                        this.setText(imbData.resourceId)
                        this.setTextColor(getColor(imbDescriptionTextColorMap[imbData.imbType] ?: R.color.black))
                    }
                    imbCoefficientTextView.text = imbData.imbCoefficient.round(1).toString()
                }
            } else {
                showError()
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        hideKeyboard(currentFocus ?: View(this))
        return super.dispatchTouchEvent(event)
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun isEnteredValuesValid(vararg values: Double?) : Boolean {
        return values.all { it != null }
    }

    private fun showError() {
        Toast.makeText(this,
                        getString(R.string.error_text),
                        Toast.LENGTH_SHORT).show()
    }

    private fun Double.round(decimals: Int): Double = "%.${decimals}f".format(this).toDouble()
}