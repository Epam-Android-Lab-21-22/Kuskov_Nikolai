package com.nkuskov.epam_hw

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nkuskov.epam_hw.databinding.LinearButtonLayoutBinding
import com.nkuskov.epam_hw.databinding.LinearCheckboxLayoutBinding
import com.nkuskov.epam_hw.databinding.LinearTitleLayoutBinding

class VerticalRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<VerticalItem> = mutableListOf()

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
                buttonAction = { position -> removeItem(position)}
            )
            VerticalItemType.CHECKBOX.ordinal -> CheckboxViewHolder(
                viewBinding = LinearCheckboxLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                checkAction = {position, isChecked -> changeCheckedStatus(position, isChecked)}
            )
            else -> throw Exception("Not valid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(items[position] as VerticalItem.TitleItem)
            is CheckboxViewHolder -> holder.bind(items[position] as VerticalItem.CheckboxItem)
            is ButtonViewHolder -> holder.bind(items[position] as VerticalItem.ButtonItem)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is VerticalItem.TitleItem -> VerticalItemType.TITLE.ordinal
            is VerticalItem.ButtonItem -> VerticalItemType.BUTTON.ordinal
            is VerticalItem.CheckboxItem -> VerticalItemType.CHECKBOX.ordinal
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpItems(items: List<VerticalItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun changeCheckedStatus(position: Int, isChecked: Boolean) {
        val newItem = (items[position] as VerticalItem.CheckboxItem).copy(isChecked = isChecked)
        items.removeAt(position)
        items.add(position, newItem)
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

        fun bind(item: VerticalItem.CheckboxItem) {
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

        fun bind(item: VerticalItem.ButtonItem) {
            viewBinding.buttonItemTextView.text = item.info
            viewBinding.linerLayoutButton.setOnClickListener(null)
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