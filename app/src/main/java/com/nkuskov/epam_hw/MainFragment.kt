package com.nkuskov.epam_hw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nkuskov.epam_hw.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private var state = FragmentState.RED

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CURRENT_NUMBER, binding.countTextView.text.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countTextView.text = savedInstanceState?.getString(KEY_CURRENT_NUMBER)
            ?: (parentFragmentManager.backStackEntryCount - 1).toString()
        state = FragmentState.values()
            .first { it.ordinal == arguments?.getInt(KEY_CURRENT_STATE) ?: FragmentState.RED.ordinal }
        binding.root.setBackgroundColor(
            resources.getColor(
                getColorIdFromState(state), null
            )
        )

        view.setOnClickListener {
            (activity as? FragmentCallbacksHandler)?.onFragmentClicked(state)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val KEY_CURRENT_STATE = "KEY_CURRENT_STATE"
        const val KEY_CURRENT_NUMBER = "KEY_CURRENT_NUMBER"

        private fun getColorIdFromState(state: FragmentState): Int {
            return when (state) {
                FragmentState.RED -> R.color.red
                FragmentState.BLUE -> R.color.blue
                FragmentState.GREEN -> R.color.green
            }
        }
    }
}