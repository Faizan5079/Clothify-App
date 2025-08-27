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
import com.ali.clothsapp.databinding.CartProductItemBinding

class CartProductAdapter : RecyclerView.Adapter<CartProductAdapter.CartProductsViewHolder>() {

    inner class CartProductsViewHolder(val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartProduct: CartProduct) {
            binding.apply {
                // Load product image
                Glide.with(itemView).load(cartProduct.product.images[0]).into(imageCartProduct)

                // Set product name and quantity
                tvProductCartName.text = cartProduct.product.name
                tvCartProductQuantity.text = cartProduct.quantity.toString()

                // ✅ Proper discounted price calculation
                val offer = cartProduct.product.offerPercentage
                val originalPrice = cartProduct.product.price
                val discountedPrice = if (offer != null && offer in 0f..100f) {
                    originalPrice * (1 - offer / 100f)
                } else {
                    originalPrice
                }

                tvProductCartPrice.text = "$ ${String.format("%.2f", discountedPrice)}"

                // Set selected color or fallback to transparent
                val color = cartProduct.selectedColor ?: Color.TRANSPARENT
                imageCartProductColor.setImageDrawable(ColorDrawable(color))

                // Set size or empty if null, and adjust color
                val size = cartProduct.selectedSize ?: ""
                tvCartProductSize.text = size

                if (size.isEmpty()) {
                    imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CartProduct>() {
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductsViewHolder {
        return CartProductsViewHolder(
            CartProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartProductsViewHolder, position: Int) {
        val cartProduct = differ.currentList[position]
        holder.bind(cartProduct)

        // Product click
        holder.itemView.setOnClickListener {
            onProductClick?.invoke(cartProduct)
        }

        // Quantity increase
        holder.binding.imagePlus.setOnClickListener {
            onPlusClick?.invoke(cartProduct)
        }

        // Quantity decrease
        holder.binding.imageMinus.setOnClickListener {
            onMinusClick?.invoke(cartProduct)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    // Click listeners
    var onProductClick: ((CartProduct) -> Unit)? = null
    var onPlusClick: ((CartProduct) -> Unit)? = null
    var onMinusClick: ((CartProduct) -> Unit)? = null
}
