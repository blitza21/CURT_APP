package com.example.curt_app.rawmaterials.edit

import com.example.curt_app.model.db.RawMaterialDao
import com.example.curt_app.model.RawMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditRawMaterialPresenter (private val view : EditRawMaterialView, private val rawMaterialDao: RawMaterialDao) {

    suspend fun update(rawMaterial: RawMaterial){
        val result = rawMaterialDao.updateItem(rawMaterial)
        withContext(Dispatchers.Main){
            if (result > 0) {
                view.showMessage("Updated Successfully!")
            }
            else {
                view.showMessage("An error occurred!")
            }
        }
    }
}