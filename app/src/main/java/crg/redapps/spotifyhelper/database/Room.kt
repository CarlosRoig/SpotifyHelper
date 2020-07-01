package crg.redapps.spotifyhelper.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SongsDao {
    @Query("SELECT * FROM databasesong")
    fun getAllSongs(): LiveData<List<DatabaseSong>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(vararg songs: DatabaseSong)
}

@Database(entities = [DatabaseSong::class], version = 1)
abstract class SongsDatabase : RoomDatabase() {
    abstract val songsDao: SongsDao
}

private lateinit var INSTANCE: SongsDatabase

fun getDatabase(context: Context): SongsDatabase {
    synchronized(SongsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(context.applicationContext, SongsDatabase::class.java, "songs")
                    .build()
        }
    }
    return INSTANCE
}