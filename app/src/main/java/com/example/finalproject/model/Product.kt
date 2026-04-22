package com.example.finalproject.model

import java.io.Serializable

/**
 * W2026 MAD302-01 Android Development
 * Final Project
 *
 * Name: Nithin Amin
 * Student ID: a00194322
 * Date: April 2026
 *
 * Description:
 * This data class represents a product object used in the inventory app.
 * It stores the product id, name, price, and description.
 */
data class Product(
    var id: Int,
    var name: String,
    var price: Double,
    var description: String
) : Serializable