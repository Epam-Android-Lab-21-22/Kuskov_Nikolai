package com.nkuskov.epam_hw

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nkuskov.epam_hw.databinding.FragmentVerticalRecyclerViewBinding

class VerticalRecyclerViewFragment : Fragment() {

    private var _binding: FragmentVerticalRecyclerViewBinding? = null
    private lateinit var verticalRecyclerViewAdapter: VerticalRecyclerViewAdapter

    private val binding get() = _binding!!
    private val recyclerViewItems = mutableListOf(
        VerticalItem.TitleItem(getString(R.string.schedule)),
        VerticalItem.CheckboxItem(getString(R.string.come_to_the_work), false),
        VerticalItem.CheckboxItem(getString(R.string.drink_coffee), false),
        VerticalItem.CheckboxItem(getString(R.string.have_a_dinner), false),
        VerticalItem.CheckboxItem(getString(R.string.go_home), false),
        VerticalItem.TitleItem(getString(R.string.shops)),
        VerticalItem.ButtonItem(getString(R.string.ok)),
        VerticalItem.ButtonItem(getString(R.string.fiveterochka)),
        VerticalItem.ButtonItem(getString(R.string.magnit)),
        VerticalItem.ButtonItem(getString(R.string.crossroad)),
        VerticalItem.ButtonItem(getString(R.string.spar))
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerticalRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.verticalRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context)
            verticalRecyclerViewAdapter = VerticalRecyclerViewAdapter()
            adapter = verticalRecyclerViewAdapter
            verticalRecyclerViewAdapter.setUpItems(recyclerViewItems)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.verticalRecyclerView.adapter = null
        _binding = null
    }
}