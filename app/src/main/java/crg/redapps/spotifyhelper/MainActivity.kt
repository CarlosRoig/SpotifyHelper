package crg.redapps.spotifyhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("CARLOS: Log into the app")
        setContentView(R.layout.activity_main)
    }
}