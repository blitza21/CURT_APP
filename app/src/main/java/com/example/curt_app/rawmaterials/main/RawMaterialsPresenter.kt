package com.example.curt_app.rawmaterials.main

import com.example.curt_app.model.db.RawMaterialDao
import com.example.curt_app.model.ElectricPart
import com.example.curt_app.model.RawMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RawMaterialsPresenter(private val view : RawMaterialsView, private val rawMaterialDao: RawMaterialDao) {

    suspend fun getAll(){
        val rawMaterialsList = rawMaterialDao.getAll()
        withContext(Dispatchers.Main){
            view.showData(rawMaterialsList)
        }
    }


    suspend fun deleteAll(){
        val result = rawMaterialDao.deleteAll()
        withContext(Dispatchers.Main){
            if (result > 0)
                view.showMessage("Deleted all items successfully!")
            else
                view.showMessage("No items added yet!")
        }
    }

    suspend fun deleteItem(rawMaterial: RawMaterial){
        val result = rawMaterialDao.deleteItem(rawMaterial)
        withContext(Dispatchers.Main){
            view.showMessage("Deleted successfully!")
        }
    }
}