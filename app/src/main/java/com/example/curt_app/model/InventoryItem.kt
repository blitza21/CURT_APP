package com.example.curt_app.model

import androidx.room.PrimaryKey

abstract class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    var name : String ,
    var quantity : Int
)