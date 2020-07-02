package crg.redapps.spotifyhelper.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import crg.redapps.spotifyhelper.R
import crg.redapps.spotifyhelper.databinding.FragmentSongDetailsBinding
import crg.redapps.spotifyhelper.viewmodels.SongDetailsViewModel

class SongDetailsFragment : Fragment() {

    private lateinit var viewModel: SongDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val song = SongDetailsFragmentArgs.fromBundle(arguments!!).selectedSong
        val application = requireNotNull(activity).application
        val binding = FragmentSongDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = SongDetailsViewModel.Factory(song, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SongDetailsViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }

}