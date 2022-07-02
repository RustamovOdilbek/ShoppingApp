package com.exsample.unittest.repositories

import androidx.lifecycle.LiveData
import com.exsample.unittest.data.local.ShoppingDao
import com.exsample.unittest.data.local.ShoppingItem
import com.exsample.unittest.data.remote.responses.ImageResponse
import com.exsample.unittest.data.remote.responses.PixabayAPI
import com.exsample.unittest.utils.Resourse
import retrofit2.Response
import javax.inject.Inject

class DefoultShoppingRepository
@Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI): ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resourse<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resourse.success(it)
                }?: Resourse.error("An unkown error occured", null)
            }else{
                Resourse.error("An unkown error occured", null)
            }
        }catch (e: Exception){
            Resourse.error("Could'n reach thr server. Check your internet connection", null)
        }
    }


}