package com.ali.clothsapp.Data


sealed class Category(val category: String) {
    object Cargos: Category("Cargos")
    object Topwears: Category("Topwears")
    object Hoodies: Category("Hoodies")
    object Jeans: Category("Jeans")
    object Joggers: Category("Joggers")
    object Trousers: Category("Trousers")
}