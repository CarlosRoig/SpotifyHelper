package crg.redapps.spotifyhelper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import crg.redapps.spotifyhelper.database.SongsDatabase
import crg.redapps.spotifyhelper.database.asDomainModel
import crg.redapps.spotifyhelper.domain.Song
import crg.redapps.spotifyhelper.network.Network
import crg.redapps.spotifyhelper.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class SongsRepository(private val database: SongsDatabase) {
    val songs: LiveData<List<Song>> = Transformations.map(database.songsDao.getAllSongs()) {
        it.asDomainModel()
    }

    suspend fun refreshSongs() {
        Timber.i("CARLOS: Refreshing Songs")
        withContext(Dispatchers.IO) {
            val songs = Network.spotifyService.getMostPlayedSongs().await()
            database.songsDao.insertSongs(*songs.asDatabaseModel())
        }
    }

}