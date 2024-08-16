package com.example.curt_app.electricparts.edit

import com.example.curt_app.model.db.ElectricPartDao
import com.example.curt_app.model.ElectricPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditElectricPartPresenter(private val view : EditElectricPartView, private val electricPartDao: ElectricPartDao) {

    suspend fun update(electricPart : ElectricPart){
        val result = electricPartDao.updateItem(electricPart)
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