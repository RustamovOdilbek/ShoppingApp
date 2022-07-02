package com.exsample.unittest.ui.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsample.unittest.data.local.ShoppingItem
import com.exsample.unittest.data.remote.responses.ImageResponse
import com.exsample.unittest.repositories.ShoppingRepository
import com.exsample.unittest.utils.Constants
import com.exsample.unittest.utils.Event
import com.exsample.unittest.utils.Resourse
import kotlinx.coroutines.launch

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShoppingRepository
)
: ViewModel() {

    val shopingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resourse<ImageResponse>>>()
    val images: LiveData<Event<Resourse<ImageResponse>>> = _images


    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl


    private val _insertShoppingItemStatus = MutableLiveData<Event<Resourse<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resourse<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url: String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

     fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String){
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resourse.error("The filed must not be empty", null)))
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resourse.error("The name of thr item "+
                    "must not exced ${Constants.MAX_NAME_LENGTH} characters", null)))
            return
        }

        if (priceString.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resourse.error("The price of thr item "+
                    "must not exced ${Constants.MAX_PRICE_LENGTH} characters", null)))
            return
        }

        val amount = try {
            amountString.toInt()
        }catch (e: Exception){
            _insertShoppingItemStatus.postValue(Event(Resourse.error("Plase enter a  valid amount ", null)))
            return
        }

        val shoppingItem = ShoppingItem(name, amount, priceString.toFloat(), _curImageUrl.value?:"")
        insertShoppingItemIntoDb(shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resourse.success(shoppingItem)))
    }

    fun searchImage(imageQuarey: String){
        if (imageQuarey.isEmpty()){
            return
        }
        _images.value = Event(Resourse.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuarey)
            _images.value = Event(response)
        }
    }

}