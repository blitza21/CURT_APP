package com.example.curt_app.mechanicalparts.add

import com.example.curt_app.model.db.MechanicalPartDao
import com.example.curt_app.model.ElectricPart
import com.example.curt_app.model.MechanicalPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddMechanicalPartPresenter(private val view : AddMechanicalPartView, private val mechanicalPartDao: MechanicalPartDao) {

    suspend fun add(mechanicalPart : MechanicalPart){
        val result = mechanicalPartDao.addItem(mechanicalPart)
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