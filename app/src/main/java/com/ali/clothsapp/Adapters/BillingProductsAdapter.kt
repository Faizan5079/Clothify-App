package com.ali.clothsapp.Adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ali.clothsapp.Data.CartProduct
import com.ali.clothsapp.databinding.BillingProductsRvItemBinding

class BillingProductsAdapter : RecyclerView.Adapter<BillingProductsAdapter.BillingProductsViewHolder>() {

    inner class BillingProductsViewHolder(val binding: BillingProductsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartProduct: CartProduct) {
            binding.apply {
                // Load product image
                Glide.with(itemView).load(cartProduct.product.images[0]).into(imageCartProduct)

                // Set name and quantity
                tvProductCartName.text = cartProduct.product.name
                tvBillingProductQuantity.text = cartProduct.quantity.toString()

                // Calculate discounted price
                val offer = cartProduct.product.offerPercentage
                val originalPrice = cartProduct.product.price
                val finalPrice = if (offer != null && offer in 0f..100f) {
                    originalPrice * (1 - offer / 100f)
                } else {
                    originalPrice
                }

                tvProductCartPrice.text = "$ ${String.format("%.2f", finalPrice)}"

                // Set selected color or transparent if null
                val color = cartProduct.selectedColor ?: Color.TRANSPARENT
                imageCartProductColor.setImageDrawable(ColorDrawable(color))

                // Set size text or empty string if null
                tvCartProductSize.text = cartProduct.selectedSize ?: ""

                // Set a transparent box if no size selected
                if (cartProduct.selectedSize.isNullOrEmpty()) {
                    imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingProductsViewHolder {
        return BillingProductsViewHolder(
            BillingProductsRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BillingProductsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size
}
