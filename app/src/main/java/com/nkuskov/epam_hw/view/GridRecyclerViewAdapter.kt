package com.nkuskov.epam_hw.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nkuskov.epam_hw.databinding.GridItemLayoutBinding
import com.nkuskov.epam_hw.databinding.GridPlaceholderLayoutBinding
import com.nkuskov.epam_hw.model.GridItem
import com.nkuskov.epam_hw.presenter.GridRecyclerViewPresenter

class GridRecyclerViewAdapter(private val presenter: GridRecyclerViewPresenter) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            GridItemType.DEFAULT.ordinal -> GridItemViewHolder(
                GridItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GridItemType.PLACEHOLDER.ordinal -> GridPlaceholderViewHolder(
                GridPlaceholderLayoutBinding.inflate(LayoutInflater.from(parent.context))
            )
            else -> throw Exception("Not valid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GridItemViewHolder) holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (presenter.getItems()[position]) {
            is GridItem.DefaultItem -> GridItemType.DEFAULT.ordinal
            is GridItem.Placeholder -> GridItemType.PLACEHOLDER.ordinal
        }
    }

    override fun getItemCount(): Int = presenter.getItems().size

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    fun addNewItem(position: Int) {
        notifyItemInserted(position)
    }

    class GridItemViewHolder(
        private val viewBinding: GridItemLayoutBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(number: Int) {
            viewBinding.numberTextView.text = number.toString()
        }
    }

    class GridPlaceholderViewHolder(
        private val viewBinding: GridPlaceholderLayoutBinding
    ) : RecyclerView.ViewHolder(viewBinding.root)
}

private enum class GridItemType {
    DEFAULT,
    PLACEHOLDER
}