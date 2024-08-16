package com.example.curt_app.electricparts.main

import com.example.curt_app.model.db.ElectricPartDao
import com.example.curt_app.model.ElectricPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectricPartsPresenter(private val view : ElectricPartsView, private val electricPartDao: ElectricPartDao) {

    suspend fun getAll(){
        val electricPartsList = electricPartDao.getAll()
        withContext(Dispatchers.Main){
            view.showData(electricPartsList)
        }
    }


    suspend fun deleteAll(){
        val result = electricPartDao.deleteAll()
        withContext(Dispatchers.Main){
            if (result > 0)
                view.showMessage("Deleted all items successfully!")
            else
                view.showMessage("No items added yet!")
        }
    }

    suspend fun deleteItem(electricPart: ElectricPart){
        val result = electricPartDao.deleteItem(electricPart)
        withContext(Dispatchers.Main){
            view.showMessage("Deleted successfully!")
        }
    }
}