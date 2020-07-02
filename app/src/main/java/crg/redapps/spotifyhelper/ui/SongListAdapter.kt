package crg.redapps.spotifyhelper.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import crg.redapps.spotifyhelper.R
import crg.redapps.spotifyhelper.databinding.SongItemListBinding
import crg.redapps.spotifyhelper.domain.Song

class SongListAdapter(private val onClickListener: SongClick) :
    ListAdapter<Song, SongListAdapter.SongViewHolder>(DiffCallback) {

    var songs: List<Song> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object DiffCallback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val withDataBinding: SongItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), SongViewHolder.LAYOUT, parent, false
        )
        return SongViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.binding.also {
            it.song = songs[position]
            it.songClick = onClickListener
        }
    }

    class SongViewHolder(var binding: SongItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.song_item_list
        }
    }

    class SongClick(val clickListener: (song: Song) -> Unit) {
        fun onCLick(song: Song) = clickListener(song)
    }

    override fun getItemCount() = songs.size

}
