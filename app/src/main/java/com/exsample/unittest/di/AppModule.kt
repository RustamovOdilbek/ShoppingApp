package com.exsample.unittest.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exsample.unittest.R
import com.exsample.unittest.data.local.ShoppingDao
import com.exsample.unittest.data.local.ShoppingItemDatabase
import com.exsample.unittest.data.remote.responses.PixabayAPI
import com.exsample.unittest.repositories.DefoultShoppingRepository
import com.exsample.unittest.repositories.ShoppingRepository
import com.exsample.unittest.utils.Constants.BASE_URL
import com.exsample.unittest.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME)

    @Singleton
    @Provides
    fun shoppingDefualtShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefoultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()


    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}