package com.exsample.unittest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.exsample.unittest.R
import com.exsample.unittest.databinding.FragmentAddShoppingItemBinding
import com.exsample.unittest.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    private lateinit var binding: FragmentAddShoppingItemBinding
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddShoppingItemBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        subscribeToObserves()

        binding.apply {
            btnAddShoppingItem.setOnClickListener {
                viewModel.insertShoppingItem(
                    etShoppingItemName.text.toString(),
                    etShoppingItemAmount.text.toString(),
                    etShoppingItemPrice.text.toString()
                )
            }
        }

        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(R.id.action_addShoppingItemFragment_to_imagePickFragment)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObserves(){
        viewModel.curImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(binding.ivShoppingImage)
        })
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHamdler()?.let { result->
                when(result.status){
                    Status.SUCCESS ->{
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message?: "Added Shopping Item",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR ->{
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message?: "An unkown error occured",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING ->{
                        /*  NO-OP */
                    }
                }
            }
        })
    }

}