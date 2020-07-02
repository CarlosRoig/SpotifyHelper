package crg.redapps.spotifyhelper.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import crg.redapps.spotifyhelper.R
import crg.redapps.spotifyhelper.databinding.FragmentHomeBinding
import crg.redapps.spotifyhelper.databinding.SongItemListBinding
import crg.redapps.spotifyhelper.domain.Song
import crg.redapps.spotifyhelper.viewmodels.HomeViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private var songListAdapter: SongListAdapter? = null

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("CARLOS: On home Create view")
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        songListAdapter = SongListAdapter(SongListAdapter.SongClick {
            viewModel.navigateToSongDetails(it)
        })

        binding.homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = songListAdapter
        }

        viewModel.navigateToSong.observe(this, Observer {
            it?.let {
                Toast.makeText(context, "Song ${it.name} clicked", Toast.LENGTH_SHORT).show()
            }
//            viewModel.navigatedToDetails()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.songs.observe(viewLifecycleOwner, Observer { newSongs ->
            newSongs?.apply {
                songListAdapter?.songs = newSongs
            }
        })

    }
}

class SongListAdapter(private val onClickListener: SongClick) :
    RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var songs: List<Song> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

//    companion object DiffCallback : DiffUtil.ItemCallback<Song>() {
//        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
//            return oldItem.uri == newItem.uri
//        }
//
//        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
//            return oldItem == newItem
//        }
//    }

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
