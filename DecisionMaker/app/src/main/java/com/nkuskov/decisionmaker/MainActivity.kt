package com.nkuskov.decisionmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var decisionTextView: TextView
    private lateinit var decisionButton: Button
    private lateinit var decisionCounterTextView: TextView
    private var decisionsCount = 0

    enum class PartType {
        First,
        Second
    }

    private val decisionTextMap = listOf(DecisionTextData(R.string.you_need, PartType.First),
                                         DecisionTextData(R.string.advise_you, PartType.First),
                                         DecisionTextData(R.string.terrible_mistake, PartType.First),
                                         DecisionTextData(R.string.need_immediately, PartType.First),
                                         DecisionTextData(R.string.very_risky, PartType.First),
                                         DecisionTextData(R.string.impossible, PartType.First),
                                         DecisionTextData(R.string.best_sneakily, PartType.First),
                                         DecisionTextData(R.string.what_you_want, PartType.First),
                                         DecisionTextData(R.string.yourself_know_what_follows, PartType.First),
                                         DecisionTextData(R.string.i_command, PartType.First),
                                         DecisionTextData(R.string.do_it_right_now, PartType.Second),
                                         DecisionTextData(R.string.think_it_over_again, PartType.Second),
                                         DecisionTextData(R.string.provide_an_escape_route, PartType.Second),
                                         DecisionTextData(R.string.jump_into_the_pool_with, PartType.Second),
                                         DecisionTextData(R.string.hurry_to_make_decision, PartType.Second),
                                         DecisionTextData(R.string.forget_about_it, PartType.Second),
                                         DecisionTextData(R.string.to_do_but_not_to_tell_anyone, PartType.Second),
                                         DecisionTextData(R.string.tell_a_friend, PartType.Second),
                                         DecisionTextData(R.string.listen_to_me_and_do_opposite, PartType.Second),
                                         DecisionTextData(R.string.stop_asking_me, PartType.Second))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        decisionTextView = findViewById(R.id.decision_text)
        decisionButton = findViewById(R.id.decision_button)
        decisionCounterTextView = findViewById(R.id.decision_counter_text)

        decisionButton.setOnClickListener{
            onDecisionButtonClicked()
        }
    }

    private fun onDecisionButtonClicked(){
        decisionTextView.text = getString(R.string.decision_text_placeholder, getDecisionPartText(PartType.First), getDecisionPartText(PartType.Second))

        decisionCounterTextView.text = "${++decisionsCount}"
    }

    private fun getDecisionPartText(partType: PartType) : String {
        val partTypesData = decisionTextMap.filter { it.part == partType }
        return getString(partTypesData[Random.nextInt(0, partTypesData.count())].textResourceId)
    }

    data class DecisionTextData(val textResourceId: Int, val part: PartType)
}