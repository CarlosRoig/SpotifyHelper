package crg.redapps.spotifyhelper.domain

import crg.redapps.spotifyhelper.network.NetworkSpotifyAlbum

data class Song(
    val uri: String,
    val name: String,
    val popularity: Double,
    val type: String,
    val album: String
)