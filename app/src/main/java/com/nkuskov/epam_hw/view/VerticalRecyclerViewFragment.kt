package com.nkuskov.epam_hw.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuskov.epam_hw.VerticalRecyclerViewAdapter
import com.nkuskov.epam_hw.databinding.FragmentVerticalRecyclerViewBinding
import com.nkuskov.epam_hw.presenter.VerticalRecyclerViewPresenter

class VerticalRecyclerViewFragment : Fragment(), VerticalRecyclerView {

    private var _binding: FragmentVerticalRecyclerViewBinding? = null
    private lateinit var verticalRecyclerViewAdapter: VerticalRecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerticalRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val presenter = VerticalRecyclerViewPresenter(this, MainActivity.verticalModel)
        binding.verticalRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context)
            verticalRecyclerViewAdapter = VerticalRecyclerViewAdapter(presenter)
            adapter = verticalRecyclerViewAdapter
            verticalRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.verticalRecyclerView.adapter = null
        _binding = null
    }

    override fun removeItem(position: Int) {
        requireActivity().runOnUiThread{
            verticalRecyclerViewAdapter.removeItem(position)
        }
    }

    override fun changeCheckedStatus(position: Int, isChecked: Boolean) {
        requireActivity().runOnUiThread {
            verticalRecyclerViewAdapter.changeCheckedStatus(position, isChecked)
        }
    }
}