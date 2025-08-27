package com.ali.clothsapp.Helper

/**
 * Extension function to calculate the discounted price from the offer percentage.
 *
 * @receiver Float? - The offer percentage (e.g., 35 means 35% off)
 * @param price Float - The original price of the product
 * @return Float - The price after applying the discount
 */
fun Float?.getProductPrice(price: Float): Float {
    // Return original price if offer is null, zero, or out of valid range
    if (this == null || this <= 0f || this > 100f) return price

    // Convert percentage to decimal (e.g., 35 → 0.35)
    val offerDecimal = this / 100f

    // Calculate and return discounted price
    return price * (1 - offerDecimal)
}
