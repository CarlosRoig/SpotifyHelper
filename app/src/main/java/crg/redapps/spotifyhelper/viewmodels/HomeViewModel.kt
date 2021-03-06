package crg.redapps.spotifyhelper.viewmodels

import android.app.Application
import androidx.lifecycle.*
import crg.redapps.spotifyhelper.database.getDatabase
import crg.redapps.spotifyhelper.domain.Song
import crg.redapps.spotifyhelper.repository.SongsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val homeJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(homeJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val repository = SongsRepository(database)

    private val _navigateToSong = MutableLiveData<Song>()
    val navigateToSong: LiveData<Song>
        get() = _navigateToSong

    init {
        Timber.i("CARLOS: On init of View Model")
        viewModelScope.launch {
            Timber.i("CARLOS: On init of View Model -> Inside the launch")
            repository.refreshSongs()
        }
    }

    val songs = repository.songs

    override fun onCleared() {
        super.onCleared()
        homeJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun navigateToSongDetails(song: Song) {
        _navigateToSong.value = song
    }

    fun navigatedToDetails() {
        _navigateToSong.value = null
    }

}