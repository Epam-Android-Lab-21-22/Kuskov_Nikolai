package com.nkuskov.epam_hw.presentation.song_preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuskov.epam_hw.R
import com.nkuskov.epam_hw.databinding.SongPreviewsFragmentBinding

class SongPreviewsFragment : Fragment(R.layout.song_previews_fragment) {

    private val viewModel: ISongPreviewsViewModel by activityViewModels<SongPreviewsViewModel>()
    private val songPreviewAdapter = SongPreviewAdapter(::onSongPreviewClicked)
    private var _binding: SongPreviewsFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SongPreviewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.songPreviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songPreviewAdapter
        }
        viewModel.songsPreviewUpdated.observe(viewLifecycleOwner){
            songPreviewAdapter.updateData(it)
        }
    }

    private fun onSongPreviewClicked(songId: String) {
        SongPreviewsFragmentDirections.actionSongPreviewsToSongInfo(songId).also {
            findNavController().navigate(it)
        }
    }
}