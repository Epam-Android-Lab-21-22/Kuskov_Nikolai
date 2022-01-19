package com.nkuskov.epam_hw.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuskov.epam_hw.MyApp
import com.nkuskov.epam_hw.VerticalRecyclerViewAdapter
import com.nkuskov.epam_hw.databinding.FragmentVerticalRecyclerViewBinding
import com.nkuskov.epam_hw.presenter.IVerticalRecyclerView
import com.nkuskov.epam_hw.presenter.VerticalRecyclerViewPresenter
import com.nkuskov.epam_hw.presenter.view_data.VerticalItem

class VerticalRecyclerViewFragment : Fragment(), IVerticalRecyclerView {

    private lateinit var presenter: VerticalRecyclerViewPresenter
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
        val myApp = activity?.application as? MyApp ?: return
        presenter = myApp.verticalViewPresenter
        presenter.verticalRecyclerView = this
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
        activity?.runOnUiThread{
            verticalRecyclerViewAdapter.removeItem(position)
        }
    }

    override fun updateItem(position: Int) {
        activity?.runOnUiThread{
            verticalRecyclerViewAdapter.updateItem(position)
        }
    }

    override fun changeCheckedStatus(position: Int) {
        activity?.runOnUiThread {
            verticalRecyclerViewAdapter.changeCheckedStatus(position)
        }
    }
}