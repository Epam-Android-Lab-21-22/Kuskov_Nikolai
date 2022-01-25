package com.nkuskov.epam_hw.presentation.song_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.nkuskov.epam_hw.R
import com.nkuskov.epam_hw.databinding.SongInfoFragmentBinding

class SongInfoFragment : Fragment(R.layout.song_info_fragment) {

    private val navArgs by navArgs<SongInfoFragmentArgs>()
    private val viewModel: ISongInfoViewModel by viewModels<SongInfoViewModel> {SongInfoViewModelFactory(navArgs.songId)}
    private var _binding: SongInfoFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SongInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onSongInfoGot.observe(viewLifecycleOwner){ songInfo ->
            if (songInfo!= null)
                with(songInfo) {
                    binding.songTitle.text = title
                    binding.songArtist.text = performer
                    binding.songAlbum.text = album
                    binding.songCoverUrl.text = coverUrl
                    binding.songGenre.text = genre
                    binding.songYear.text = year.toString()
                    binding.songTextTextView.text = text
                }
        }

    }

}