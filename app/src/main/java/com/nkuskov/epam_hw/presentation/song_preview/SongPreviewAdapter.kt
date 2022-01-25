package com.nkuskov.epam_hw.presentation.song_preview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nkuskov.epam_hw.databinding.SongPreviewItemLayoutBinding
import com.nkuskov.epam_hw.domain.domain_model.SongPreview

class SongPreviewAdapter(private val onSongPreviewClicked: (String) -> Unit) :
    RecyclerView.Adapter<SongPreviewAdapter.SongPreviewViewHolder>() {

    private val items = mutableListOf<SongPreview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongPreviewViewHolder {
        return SongPreviewViewHolder(
            SongPreviewItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onSongPreviewClicked
        )
    }

    override fun onBindViewHolder(holder: SongPreviewViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: Set<SongPreview>) {
        this.items.clear()
        this.items.addAll(items.toList())
        notifyDataSetChanged()
    }

    class SongPreviewViewHolder(
        private val viewBinding: SongPreviewItemLayoutBinding,
        private val onSongPreviewClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(songPreview: SongPreview) {
            viewBinding.songPreviewArtist.text = songPreview.performer
            viewBinding.songPreviewTitle.text = songPreview.title
            viewBinding.songPreviewCoverUrl.text = songPreview.coverUrl
            viewBinding.root.setOnClickListener(null)
            viewBinding.root.setOnClickListener { onSongPreviewClicked(songPreview.id) }
        }
    }
}