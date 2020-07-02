package crg.redapps.spotifyhelper.viewmodels

import android.app.Application
import androidx.lifecycle.*
import crg.redapps.spotifyhelper.domain.Song

class SongDetailsViewModel(song: Song, application: Application) : AndroidViewModel(application) {

    val _selectedSong = MutableLiveData<Song>()
    val selectedSong: LiveData<Song>
        get() = _selectedSong

    init {
        _selectedSong.value = song
    }

    class Factory(val song: Song, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SongDetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SongDetailsViewModel(song, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}