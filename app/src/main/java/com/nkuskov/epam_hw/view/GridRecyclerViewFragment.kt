package com.nkuskov.epam_hw.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.nkuskov.epam_hw.databinding.FragmentGridRecyclerViewBinding
import com.nkuskov.epam_hw.presenter.GridRecyclerViewPresenter

private const val SPAN_COUNT = 3

class GridRecyclerViewFragment : Fragment(), GridRecyclerView {
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
        val presenter = GridRecyclerViewPresenter(this, MainActivity.gridModel)
        binding.gridRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            gridLayoutAdapter = GridRecyclerViewAdapter(presenter)
            adapter = gridLayoutAdapter
        }

        binding.addNewItemButton.setOnClickListener(null)
        binding.addNewItemButton.setOnClickListener{
            presenter.addNewItem()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun updateItem(position: Int) {
        requireActivity().runOnUiThread{
            gridLayoutAdapter.updateItem(position)
            binding.gridRecyclerView.smoothScrollToPosition(gridLayoutAdapter.itemCount)
        }
    }

    override fun addNewItem(position: Int) {
        requireActivity().runOnUiThread{
            gridLayoutAdapter.addNewItem(position)
            binding.gridRecyclerView.smoothScrollToPosition(gridLayoutAdapter.itemCount)
        }
    }
}