package crg.redapps.spotifyhelper.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import timber.log.Timber
import java.io.IOException


interface ApiService {
    @GET("v1/me/top/tracks")
    fun getMostPlayedSongs(): Deferred<NetworkSongContainer>
}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

var client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        Timber.i("CARLOS: Creating Interception")
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer BQAHcqL2E_lmfKsU1poCgnrSk3hNPdJBIDl4G4HrFYs3mEzbfmhAKtTZWHqe4sBdZ2h4a15rDNCNDAsBBPfjktLMY4mATSFZq9SMfWd4HIuOxK3nrsM3rCoqhOafpmK5FStkHGRvwIf17cWkDwG9-zA")
            .build()
        return chain.proceed(newRequest)
    }
}).build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val spotifyService = retrofit.create(ApiService::class.java)
}