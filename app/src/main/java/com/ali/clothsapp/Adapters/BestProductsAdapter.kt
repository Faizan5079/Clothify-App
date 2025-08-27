package com.ali.clothsapp.Adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ali.clothsapp.Data.Product
import com.ali.clothsapp.databinding.ProductRvItemBinding

class BestProductsAdapter : RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>() {

    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                // Load product image
                Glide.with(itemView).load(product.images[0]).into(imgProduct)

                // Set product name
                tvName.text = product.name

                // Show original price
                tvPrice.text = "$ ${product.price}"
                tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                // Handle offer price if available
                product.offerPercentage?.let { offer ->
                    if (offer in 0f..100f) {
                        val priceAfterOffer = product.price * (1 - offer / 100f)
                        tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                        tvNewPrice.visibility = View.VISIBLE
                    } else {
                        // Invalid offer
                        tvNewPrice.text = "$ ${product.price}"
                        tvNewPrice.visibility = View.GONE
                    }
                } ?: run {
                    // No offer
                    tvNewPrice.visibility = View.GONE
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    var onClick: ((Product) -> Unit)? = null
}
