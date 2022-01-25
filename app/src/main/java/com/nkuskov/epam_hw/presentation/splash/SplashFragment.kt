package com.nkuskov.epam_hw.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nkuskov.epam_hw.R
import com.nkuskov.epam_hw.databinding.SplashFragmentBinding

class SplashFragment : Fragment(R.layout.splash_fragment) {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!
    private val  viewModel: ISplashViewModel by activityViewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initProgress.observe(viewLifecycleOwner) { progress ->
            activity?.runOnUiThread {
                binding.initProgressBar.progress = progress
            }
        }
        viewModel.initFinished.observe(viewLifecycleOwner) { finished ->
            if(finished)
                activity?.runOnUiThread {
                    findNavController().navigate(R.id.action_splash_to_song_previews)
                }
        }

        viewModel.init()

    }

}