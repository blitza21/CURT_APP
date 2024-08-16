package com.example.curt_app.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.curt_app.model.ElectricPart
import com.example.curt_app.model.MechanicalPart

@Dao
interface MechanicalPartDao {
    @Query("SELECT * FROM mechanical_parts_table")
    suspend fun getAll() : List<MechanicalPart>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(mechanicalPart: MechanicalPart) : Long

    @Delete
    suspend fun deleteItem(mechanicalPart: MechanicalPart) : Int

    @Query("DELETE FROM mechanical_parts_table")
    suspend fun deleteAll() : Int

    @Update
    suspend fun updateItem(mechanicalPart: MechanicalPart) : Int
}