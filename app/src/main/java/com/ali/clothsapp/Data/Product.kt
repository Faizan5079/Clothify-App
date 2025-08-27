package com.ali.clothsapp.Data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(

    val id: String,
    val name: String,
    val category: String,
    val price: Float= 0f,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors: List<Long>? = null,
    val sizes: List<String>? = null,
    val images: List<String>,
): Parcelable
{
    constructor() : this("0", "", "", 0f, images = emptyList())

}