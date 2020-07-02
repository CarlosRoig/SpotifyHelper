package crg.redapps.spotifyhelper.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val uri: String,
    val name: String,
    val popularity: Double,
    val type: String,
    val album: String
) : Parcelable