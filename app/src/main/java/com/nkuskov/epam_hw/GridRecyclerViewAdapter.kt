package com.nkuskov.epam_hw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nkuskov.epam_hw.databinding.GridItemLayoutBinding

class GridRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GridItemViewHolder(GridItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GridItemViewHolder).bind(position)
    }

    override fun getItemCount(): Int = items.size

    fun addNewItem() {
        items.add(items.size + 1)
        notifyItemInserted(items.size)
    }

    class GridItemViewHolder(
        private val viewBinding: GridItemLayoutBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(number: Int){
            viewBinding.numberTextView.text = number.toString()
        }
    }
}