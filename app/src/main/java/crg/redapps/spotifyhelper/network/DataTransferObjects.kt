package crg.redapps.spotifyhelper.network

import android.os.Parcelable
import androidx.lifecycle.Transformations
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import crg.redapps.spotifyhelper.database.DatabaseAlbum
import crg.redapps.spotifyhelper.database.DatabaseSong
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class NetworkSongContainer(val items: List<NetworkSpotifySong>)

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkSpotifySong(
    val name: String,
    val popularity: Double,
    val type: String,
    val uri: String,
    val album: NetworkSpotifyAlbum
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkSpotifyAlbum(
    val name: String,
    @Json(name = "total_tracks") val tracks: Double,
    val id: String

) : Parcelable

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