package com.nkuskov.epam_hw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nkuskov.epam_hw.databinding.FragmentGridRecyclerViewBinding

private const val SPAN_COUNT = 5

class GridRecyclerViewFragment : Fragment() {
    private var _binding: FragmentGridRecyclerViewBinding? = null
    private lateinit var gridLayoutAdapter: GridRecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGridRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            gridLayoutAdapter = GridRecyclerViewAdapter()
            adapter = gridLayoutAdapter
        }

        binding.addNewItemButton.setOnClickListener(null)
        binding.addNewItemButton.setOnClickListener{
            gridLayoutAdapter.addNewItem()
            binding.gridRecyclerView.smoothScrollToPosition(gridLayoutAdapter.itemCount)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}