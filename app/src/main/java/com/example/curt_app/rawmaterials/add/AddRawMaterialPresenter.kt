package com.example.curt_app.rawmaterials.add

import com.example.curt_app.model.db.RawMaterialDao
import com.example.curt_app.model.RawMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddRawMaterialPresenter(private val view : AddRawMaterialView, private val rawMaterialDao: RawMaterialDao) {

    suspend fun add(rawMaterial : RawMaterial){
        val result = rawMaterialDao.addItem(rawMaterial)
        withContext(Dispatchers.Main){
            if (result > 0) {
                view.showMessage("Added Successfully!")
            }
            else {
                view.showMessage("An error occurred!")
            }
        }
    }
}