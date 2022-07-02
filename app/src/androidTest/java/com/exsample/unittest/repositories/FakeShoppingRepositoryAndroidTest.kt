package com.exsample.unittest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exsample.unittest.data.local.ShoppingItem
import com.exsample.unittest.data.remote.responses.ImageResponse
import com.exsample.unittest.utils.Resourse

class FakeShoppingRepositoryAndroidTest: ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resourse<ImageResponse> {
        return if (shouldReturnNetworkError){
            Resourse.error("Error", null)
        }else{
            Resourse.success(ImageResponse(listOf(), 0, 0))
        }
    }

}