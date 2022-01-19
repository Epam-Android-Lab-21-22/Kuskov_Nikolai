package com.nkuskov.epam_hw

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nkuskov.epam_hw.databinding.LinearButtonLayoutBinding
import com.nkuskov.epam_hw.databinding.LinearCheckboxLayoutBinding
import com.nkuskov.epam_hw.databinding.LinearTitleLayoutBinding
import com.nkuskov.epam_hw.presenter.VerticalRecyclerViewPresenter
import com.nkuskov.epam_hw.presenter.view_data.VerticalItem

class VerticalRecyclerViewAdapter(private val presenter: VerticalRecyclerViewPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VerticalItemType.TITLE.ordinal -> TitleViewHolder(
                viewBinding = LinearTitleLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            VerticalItemType.BUTTON.ordinal -> ButtonViewHolder(
                viewBinding = LinearButtonLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                buttonAction = { position -> presenter.removeItem(position)}
            )
            VerticalItemType.CHECKBOX.ordinal -> CheckboxViewHolder(
                viewBinding = LinearCheckboxLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                checkAction = {position, isChecked -> presenter.changeCheckedStatus(position, isChecked)}
            )
            else -> throw Exception("Not valid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(presenter.items[position] as VerticalItem.TitleItem)
            is CheckboxViewHolder -> holder.bind(presenter.items[position] as VerticalItem.ToDoItem)
            is ButtonViewHolder -> holder.bind(presenter.items[position] as VerticalItem.ShopItem)
        }
    }

    override fun getItemCount(): Int = presenter.items.size

    override fun getItemViewType(position: Int): Int {
        return when (presenter.items[position]) {
            is VerticalItem.TitleItem -> VerticalItemType.TITLE.ordinal
            is VerticalItem.ShopItem -> VerticalItemType.BUTTON.ordinal
            is VerticalItem.ToDoItem -> VerticalItemType.CHECKBOX.ordinal
        }
    }

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    fun changeCheckedStatus(position: Int) {
        notifyItemChanged(position)
    }

    class TitleViewHolder(
        private val viewBinding: LinearTitleLayoutBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: VerticalItem.TitleItem) {
            viewBinding.titleItemTextView.text = item.title
        }
    }

    class CheckboxViewHolder(
        private val viewBinding: LinearCheckboxLayoutBinding,
        private val checkAction: (Int, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: VerticalItem.ToDoItem) {
            viewBinding.checkBoxItemTextView.text = item.note
            viewBinding.checkBox.setOnCheckedChangeListener(null)
            viewBinding.checkBox.isChecked = item.isChecked
            viewBinding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                checkAction(adapterPosition, isChecked)
            }
        }
    }

    class ButtonViewHolder(
        private val viewBinding: LinearButtonLayoutBinding,
        private val buttonAction: (Int) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: VerticalItem.ShopItem) {
            viewBinding.buttonItemTextView.text = item.info
            viewBinding.linerLayoutButton.setOnClickListener(null)
            if(item.loading){
                viewBinding.linerLayoutButton.visibility = INVISIBLE
                viewBinding.linearLayoutProgress.visibility = VISIBLE
            }
            else {
                viewBinding.linerLayoutButton.visibility = VISIBLE
                viewBinding.linearLayoutProgress.visibility = INVISIBLE
            }
            viewBinding.linerLayoutButton.setOnClickListener {
                buttonAction(adapterPosition)
            }
        }
    }
}

private enum class VerticalItemType {
    TITLE,
    CHECKBOX,
    BUTTON
}