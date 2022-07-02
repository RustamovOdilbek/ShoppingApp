package com.exsample.unittest.repositories

import androidx.lifecycle.LiveData
import com.exsample.unittest.data.local.ShoppingItem
import com.exsample.unittest.data.remote.responses.ImageResponse
import com.exsample.unittest.utils.Resourse

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)


    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)


    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>


    fun observeTotalPrice(): LiveData<Float>


    suspend fun searchForImage(imageQuery: String): Resourse<ImageResponse>

}