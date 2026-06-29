package com.lihan.lazypizza.menu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lihan.lazypizza.R

enum class ProductType {
    Pizza,Drinks,Sauces,IceCream,Other;
    companion object{
        @Composable
        fun ProductType.toTypeName(): String {
            return when(this){
                Pizza -> stringResource(R.string.pizza)
                Drinks -> stringResource(R.string.drinks)
                Sauces -> stringResource(R.string.sauces)
                IceCream -> stringResource(R.string.ice_cream)
                else -> ""
            }
        }

        fun fromString(category: String): ProductType {
            return when(category.lowercase()){
                "pizza" -> Pizza
                "drinks", "drink" -> Drinks
                "ice cream", "ice_cream" -> IceCream
                "sauces", "sauce" -> Sauces
                else -> Pizza
            }
        }

        fun getFilterTypes(): List<ProductType>{
            return ProductType.entries.filter { it != Other }
        }
    }
}