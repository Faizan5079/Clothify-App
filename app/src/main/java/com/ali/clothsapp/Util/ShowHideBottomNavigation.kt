package com.ali.clothsapp.Util

import android.view.View
import androidx.fragment.app.Fragment
import com.ali.clothsapp.R
import com.ali.clothsapp.Activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.ali.clothsapp.R.id.bottom_navigation
        )
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.ali.clothsapp.R.id.bottom_navigation
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}

