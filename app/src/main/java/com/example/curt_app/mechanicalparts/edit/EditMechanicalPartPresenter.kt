package com.example.curt_app.mechanicalparts.edit

import com.example.curt_app.model.db.MechanicalPartDao
import com.example.curt_app.model.ElectricPart
import com.example.curt_app.model.MechanicalPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EditMechanicalPartPresenter(private val view : EditMechanicalPartView, private val mechanicalPartDao: MechanicalPartDao) {
    suspend fun update(mechanicalPart : MechanicalPart){
        val result = mechanicalPartDao.updateItem(mechanicalPart)
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