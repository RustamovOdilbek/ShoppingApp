package com.exsample.unittest.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exsample.unittest.MainCoroutineRule
import com.exsample.unittest.getOrAwaitValueTest
import com.exsample.unittest.repositories.FakeShoppingRepository
import com.exsample.unittest.utils.Constants
import com.exsample.unittest.utils.Status
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @After
    fun tearDown(){
        viewModel
    }

    @Test
    fun `insert shopping item with empty field, return error`(){
        viewModel.insertShoppingItem("name", "", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHamdler()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, return error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1){
                append(i)
            }
        }

        viewModel.insertShoppingItem( string, "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHamdler()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, return error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1){
                append(i)
            }
        }

        viewModel.insertShoppingItem("name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHamdler()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, return error`(){
        viewModel.insertShoppingItem("name", "77777777777777779", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHamdler()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, return success`(){
        viewModel.insertShoppingItem("name", "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHamdler()?.status).isEqualTo(Status.SUCCESS)
    }

}