package com.example.curt_app.model

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "mechanical_parts_table")
class MechanicalPart(
    name : String ,
    quantity : Int ,
    var material : String,
    var dimensions : String,
    var weight : Double,
    id : Int  = 0
) : InventoryItem(id , name , quantity) , Serializable