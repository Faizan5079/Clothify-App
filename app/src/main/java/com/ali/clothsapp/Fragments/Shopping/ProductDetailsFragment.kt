package com.ali.clothsapp.Fragments.Shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.clothsapp.R
import com.ali.clothsapp.Adapters.ColorsAdapter
import com.ali.clothsapp.Adapters.SizesAdapter
import com.ali.clothsapp.Adapters.ViewPager2Images
import com.ali.clothsapp.Data.CartProduct
import com.ali.clothsapp.Fragments.SignIn.revertAnimation
import com.ali.clothsapp.Fragments.SignIn.startAnimation
import com.ali.clothsapp.databinding.FragmentProductDetailsBinding
import com.ali.clothsapp.Util.Resource
import com.ali.clothsapp.Util.hideBottomNavigationView
import com.ali.clothsapp.Viewmodel.DetailsViewModel
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColor: Int? = null
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
        setupColorsRv()
        setupViewpager()

        // Close button click
        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        // Handle size selection
        sizesAdapter.onItemClick = {
            selectedSize = it
        }

        // Handle color selection
        colorsAdapter.onItemClick = {
            selectedColor = it
        }

        // Add to cart button click
        binding.buttonAddToCart.setOnClickListener {
            viewModel.addUpdateProductInCart(
                CartProduct(product, 1, selectedColor, selectedSize)
            )
        }

        // Observe Add to Cart state
        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        // Start loading animation
                        binding.buttonAddToCart.startAnimation()
                    }
                    is Resource.Success -> {
                        // Stop animation
                        binding.buttonAddToCart.revertAnimation()
                        binding.buttonAddToCart.setBackgroundColor(resources.getColor(R.color.black))

                        // Show Toast on success
                        Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        // Stop animation & show error
                        binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        // Bind product data to views
        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description

            if (product.colors.isNullOrEmpty())
                tvProductColors.visibility = View.INVISIBLE
            if (product.sizes.isNullOrEmpty())
                tvProductSize.visibility = View.INVISIBLE
        }

        // Set images, colors, and sizes
        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.map { it.toInt() }?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }
    }

    private fun setupViewpager() {
        binding.viewPagerProductImages.adapter = viewPagerAdapter
    }

    private fun setupColorsRv() {
        binding.rvColors.apply {
            adapter = colorsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupSizesRv() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}

// Stop animation extension function
private fun MaterialButton.stopAnimation() {
    this.revertAnimation()
}
