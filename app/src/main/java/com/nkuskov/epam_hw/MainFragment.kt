package com.nkuskov.epam_hw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainFragment : Fragment(R.layout.fragment_main) {
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view?.findViewById<TextView>(R.id.count_text_view)?.text =
            (savedInstanceState?.getInt(MainActivity.KEY_FRAGMENT_NUMBER) ?: 0).toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MainActivity.KEY_FRAGMENT_NUMBER, index)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.count_text_view).text =
            (arguments?.getInt(MainActivity.KEY_FRAGMENT_NUMBER) ?: 0).toString()
        view.setBackgroundColor(
            resources.getColor(
                arguments?.getInt(MainActivity.KEY_FRAGMENT_COLOR_ID) ?: R.color.red, null
            )
        )
        view.setOnClickListener {
            (activity as FragmentCallbacksHandler)?.onFragmentClicked()
        }
    }
}