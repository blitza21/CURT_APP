package com.example.curt_app.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.curt_app.model.ElectricPart
import com.example.curt_app.model.MechanicalPart
import com.example.curt_app.model.RawMaterial

@Database(entities = [ElectricPart::class , MechanicalPart::class , RawMaterial::class] , version = 1)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun getElectricPartDao() : ElectricPartDao
    abstract fun getMechanicalPartDao() : MechanicalPartDao
    abstract fun getRawMaterialDao() : RawMaterialDao
    companion object{
        @Volatile
        private var INSTANCE : InventoryDatabase? = null

        fun getInstance(context: Context) : InventoryDatabase {
            return INSTANCE ?: synchronized(this){
                val tempInstance = Room.databaseBuilder(
                    context,
                    InventoryDatabase::class.java,
                    "inventory_db"
                ).build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }
}