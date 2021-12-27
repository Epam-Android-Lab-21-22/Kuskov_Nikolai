package com.nkuskov.epam_hw

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nkuskov.epam_hw.databinding.LinearButtonLayoutBinding
import com.nkuskov.epam_hw.databinding.LinearCheckboxLayoutBinding
import com.nkuskov.epam_hw.databinding.LinearTitleLayoutBinding
import com.nkuskov.epam_hw.presenter.VerticalRecyclerViewPresenter
import com.nkuskov.epam_hw.model.VerticalItem

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
                buttonAction = { position -> presenter.removeItem(presenter.getItems()[position])}
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
            is TitleViewHolder -> holder.bind(presenter.getItems()[position] as VerticalItem.TitleItem)
            is CheckboxViewHolder -> holder.bind(presenter.getItems()[position] as VerticalItem.CheckboxItem)
            is ButtonViewHolder -> holder.bind(presenter.getItems()[position] as VerticalItem.ButtonItem)
        }
    }

    override fun getItemCount(): Int = presenter.getItems().size

    override fun getItemViewType(position: Int): Int {
        return when (presenter.getItems()[position]) {
            is VerticalItem.TitleItem -> VerticalItemType.TITLE.ordinal
            is VerticalItem.ButtonItem -> VerticalItemType.BUTTON.ordinal
            is VerticalItem.CheckboxItem -> VerticalItemType.CHECKBOX.ordinal
        }
    }

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }

    fun changeCheckedStatus(position: Int, isChecked: Boolean) {
        val newItem = (presenter.getItems()[position] as VerticalItem.CheckboxItem).copy(isChecked = isChecked)
        presenter.getItems().removeAt(position)
        presenter.getItems().add(position, newItem)
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
            viewBinding.linerLayoutButton.visibility = VISIBLE
            viewBinding.linearLayoutProgress.visibility = INVISIBLE
            viewBinding.linerLayoutButton.setOnClickListener {
                it.visibility = INVISIBLE
                viewBinding.linearLayoutProgress.visibility = VISIBLE
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