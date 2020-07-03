package crg.redapps.spotifyhelper.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val uri: String,
    val name: String,
    val popularity: Double,
    val type: String,
    val album: Album
) : Parcelable

@Parcelize
data class Album(
    val id: String,
    val name: String,
    val tracks: Double
) : Parcelable