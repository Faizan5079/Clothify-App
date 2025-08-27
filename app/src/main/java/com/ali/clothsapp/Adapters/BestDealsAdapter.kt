package com.ali.clothsapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ali.clothsapp.Data.Product
import com.ali.clothsapp.databinding.BestDealsRvItemBinding

class BestDealsAdapter : RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {

    inner class BestDealsViewHolder(private val binding: BestDealsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                // Load product image
                Glide.with(itemView).load(product.images[0]).into(imgBestDeal)

                // ✅ FIXED: Correct offer price calculation
                // Explanation: If offerPercentage = 20, price should be 80% of original
                // So we divide offerPercentage by 100 and subtract from 1.0f
                product.offerPercentage?.let { offer ->
                    if (offer in 0f..100f) {
                        val priceAfterOffer = product.price * (1 - offer / 100f)
                        tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                    } else {
                        // Fallback if invalid discount (e.g. 150%)
                        tvNewPrice.text = "$ ${product.price}"
                    }
                } ?: run {
                    // No discount provided, show only original price
                    tvNewPrice.text = "$ ${product.price}"
                }

                // Set other product details
                tvOldPrice.text = "$ ${product.price}"
                tvDealProductName.text = product.name
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
        return BestDealsViewHolder(
            BestDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        // Handle item click
        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Product) -> Unit)? = null
}
