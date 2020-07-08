package crg.redapps.spotifyhelper.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import crg.redapps.spotifyhelper.database.DatabaseAlbum
import crg.redapps.spotifyhelper.database.DatabaseSong

@JsonClass(generateAdapter = true)
data class NetworkSongContainer(val items: List<NetworkSpotifySong>)

@JsonClass(generateAdapter = true)
data class NetworkSpotifySong(
    val name: String,
    val popularity: Double,
    val type: String,
    val uri: String,
    val album: NetworkSpotifyAlbum
)

@JsonClass(generateAdapter = true)
data class NetworkSpotifyAlbum(
    val name: String,
    @Json(name = "total_tracks") val tracks: Double,
    val id: String

)

fun NetworkSongContainer.asDatabaseModel(): Array<DatabaseSong> {
    return items.map {
        DatabaseSong(
            uri = it.uri,
            songName = it.name,
            popularity = it.popularity,
            type = it.type,
            album = DatabaseAlbum(id = it.album.id, name = it.album.name, tracks = it.album.tracks)
        )
    }.toTypedArray()
}