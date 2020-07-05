package crg.redapps.spotifyhelper.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import crg.redapps.spotifyhelper.R
import crg.redapps.spotifyhelper.databinding.FragmentHomeBinding
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

        viewModel.navigateToSong.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSongDetailsFragment(it))
                viewModel.navigatedToDetails()
            }
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