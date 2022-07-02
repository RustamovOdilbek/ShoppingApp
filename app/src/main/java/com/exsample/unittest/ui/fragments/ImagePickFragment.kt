package com.exsample.unittest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.exsample.unittest.R
import com.exsample.unittest.adapter.ImageAdapter
import com.exsample.unittest.databinding.FragmentImagePickBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
): Fragment(R.layout.fragment_image_pick) {

    private lateinit var binding: FragmentImagePickBinding
    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImagePickBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        setupRecyclerView()

        imageAdapter.setOnItemClickListner {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setupRecyclerView(){
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(),4)
        }
    }

}