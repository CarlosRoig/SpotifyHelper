package crg.redapps.spotifyhelper.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import crg.redapps.spotifyhelper.domain.Song
import crg.redapps.spotifyhelper.network.NetworkSpotifyAlbum

@Entity
data class DatabaseSong constructor(
    @PrimaryKey
    val uri: String,
    val name: String,
    val popularity: Double,
    val type: String,
    val album: String
)

fun List<DatabaseSong>.asDomainModel(): List<Song> {
    return map {
        Song(
            uri = it.uri,
            name = it.name,
            popularity = it.popularity,
            type = it.type,
            album = it.album
        )
    }
}