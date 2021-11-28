package com.nkuskov.epam_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val decisionTextView: TextView by lazy { findViewById(R.id.decision_text) }
    private val decisionButton: Button by lazy { findViewById(R.id.decision_button) }
    private val decisionCounterTextView: TextView by lazy { findViewById(R.id.decision_counter_text) }
    private var decisionsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        decisionButton.setOnClickListener{
            onDecisionButtonClicked()
        }
    }

    private fun onDecisionButtonClicked(){
        val (firstPartResource, secondPartResource) = DecisionMaker.getDecisionResources()
        decisionTextView.text = getString(R.string.decision_text_placeholder,
                                          getString(firstPartResource),
                                          getString(secondPartResource))

        decisionCounterTextView.text = "${++decisionsCount}"
    }
}