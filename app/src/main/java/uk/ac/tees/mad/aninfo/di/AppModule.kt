package uk.ac.tees.mad.aninfo.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.aninfo.data.AnimeDatabase
import uk.ac.tees.mad.aninfo.data.AnimeRepository
import uk.ac.tees.mad.aninfo.data.AnimeRepositoryImpl
import uk.ac.tees.mad.aninfo.data.JikanApiService
import uk.ac.tees.mad.aninfo.data.WatchlistDao
import uk.ac.tees.mad.aninfo.data.WatchlistRepository
import uk.ac.tees.mad.aninfo.data.WatchlistRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun providesRetrofit() = Retrofit
        .Builder()
        .baseUrl("https://api.jikan.moe/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): JikanApiService =
        retrofit.create(JikanApiService::class.java)

    @Provides
    @Singleton
    fun provideAnimeDatabase(application: Application): AnimeDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            AnimeDatabase::class.java,
            "anime_database"
        ).build()

    @Provides
    @Singleton
    fun provideWatchlistDao(animeDatabase: AnimeDatabase): WatchlistDao =
        animeDatabase.watchlistDao()

    @Provides
    @Singleton
    fun provideWatchlistRepository(watchlistDao: WatchlistDao): WatchlistRepository =
        WatchlistRepositoryImpl(watchlistDao)

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        jikanApiService: JikanApiService
    ): AnimeRepository =
        AnimeRepositoryImpl(
            firebaseAuth = auth,
            firestore = firestore,
            apiService = jikanApiService
        )
}
