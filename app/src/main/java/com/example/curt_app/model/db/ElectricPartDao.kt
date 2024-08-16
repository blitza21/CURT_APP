package com.example.curt_app.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.curt_app.model.ElectricPart

@Dao
interface ElectricPartDao {
    @Query("SELECT * FROM electric_parts_table")
    suspend fun getAll() : List<ElectricPart>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(electricPart: ElectricPart) : Long

    @Delete
    suspend fun deleteItem(electricPart: ElectricPart) : Int

    @Query("DELETE FROM electric_parts_table")
    suspend fun deleteAll() : Int

    @Update
    suspend fun updateItem(electricPart: ElectricPart) : Int
}