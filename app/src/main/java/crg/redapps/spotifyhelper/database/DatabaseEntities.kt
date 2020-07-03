package crg.redapps.spotifyhelper.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import crg.redapps.spotifyhelper.domain.Album
import crg.redapps.spotifyhelper.domain.Song

@Entity
data class DatabaseSong constructor(
    @PrimaryKey
    val uri: String,
    val songName: String,
    val popularity: Double,
    val type: String,
    @Embedded val album: DatabaseAlbum
)

@Entity
data class DatabaseAlbum constructor(
    @PrimaryKey
    val id: String,
    val name: String,
    val tracks: Double
)

fun List<DatabaseSong>.asDomainModel(): List<Song> {
    return map {
        Song(
            uri = it.uri,
            name = it.songName,
            popularity = it.popularity,
            type = it.type,
            album = Album(id = it.album.id, name = it.album.name, tracks = it.album.tracks)
        )
    }
}