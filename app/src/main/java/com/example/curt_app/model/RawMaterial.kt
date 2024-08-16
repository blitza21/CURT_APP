package com.example.curt_app.model

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "raw_materials_table")
class RawMaterial(
    name : String,
    quantity : Int,
    var type : String,
    var purity : Double,
    id : Int = 0
) : InventoryItem(id , name , quantity) , Serializable