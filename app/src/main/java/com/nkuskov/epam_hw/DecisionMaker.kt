package com.nkuskov.epam_hw

class DecisionMaker {
    companion object {
        private val firstPartResourcesList = listOf(R.string.you_need,
                                           R.string.advise_you,
                                           R.string.terrible_mistake,
                                           R.string.need_immediately,
                                           R.string.very_risky,
                                           R.string.impossible,
                                           R.string.best_sneakily,
                                           R.string.what_you_want,
                                           R.string.yourself_know_what_follows,
                                           R.string.i_command)

        private val secondPartResourcesList = listOf(R.string.do_it_right_now,
                                                     R.string.think_it_over_again,
                                                     R.string.provide_an_escape_route,
                                                     R.string.jump_into_the_pool_with,
                                                     R.string.hurry_to_make_decision,R.string.forget_about_it,
                                                     R.string.to_do_but_not_to_tell_anyone,R.string.tell_a_friend,
                                                     R.string.listen_to_me_and_do_opposite,R.string.stop_asking_me,
                                                     R.string.ask_to_google,
                                                     R.string.ask_to_ash)

        fun getDecisionResources() : Pair<Int, Int> {
            return Pair(firstPartResourcesList.random(), secondPartResourcesList.random())
        }
    }
}