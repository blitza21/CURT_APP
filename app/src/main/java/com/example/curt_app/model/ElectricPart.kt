package com.example.curt_app.model

import android.os.Parcelable
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "electric_parts_table")
class ElectricPart(
    name : String ,
    quantity : Int ,
    var voltage : Double,
    var current : Double,
    var powerRating : Double,
    id : Int = 0
) : InventoryItem(id , name , quantity) , Serializable