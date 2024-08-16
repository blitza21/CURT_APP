package com.example.curt_app.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.curt_app.model.RawMaterial

@Dao
interface RawMaterialDao {
    @Query("SELECT * FROM raw_materials_table")
    suspend fun getAll() : List<RawMaterial>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(rawMaterial: RawMaterial) : Long

    @Delete
    suspend fun deleteItem(rawMaterial: RawMaterial) : Int

    @Query("DELETE FROM raw_materials_table")
    suspend fun deleteAll() : Int

    @Update
    suspend fun updateItem(rawMaterial: RawMaterial) : Int
}