package uk.ac.tees.mad.aninfo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.aninfo.data.AnimeRepository
import uk.ac.tees.mad.aninfo.data.AnimeRepositoryImpl
import uk.ac.tees.mad.aninfo.data.JikanApiService
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
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
        jikanApiService: JikanApiService
    ): AnimeRepository =
        AnimeRepositoryImpl(firebaseAuth = auth, firestore = firestore, apiService = jikanApiService)
}
