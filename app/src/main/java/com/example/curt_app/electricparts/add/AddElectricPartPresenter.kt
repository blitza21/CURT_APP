package com.example.curt_app.electricparts.add

import com.example.curt_app.model.db.ElectricPartDao
import com.example.curt_app.model.ElectricPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddElectricPartPresenter(private val view : AddElectricPartView, private val electricPartDao: ElectricPartDao) {

    suspend fun add(electricPart : ElectricPart){
        val result = electricPartDao.addItem(electricPart)
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