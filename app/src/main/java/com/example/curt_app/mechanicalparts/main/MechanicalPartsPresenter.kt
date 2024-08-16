package com.example.curt_app.mechanicalparts.main

import com.example.curt_app.model.db.MechanicalPartDao
import com.example.curt_app.model.MechanicalPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MechanicalPartsPresenter(private val view : MechanicalPartsView , private val mechanicalPartDao : MechanicalPartDao) {

    suspend fun getAll(){
        val mechanicalPartsList = mechanicalPartDao.getAll()
        withContext(Dispatchers.Main){
            view.showData(mechanicalPartsList)
        }
    }


    suspend fun deleteAll(){
        val result = mechanicalPartDao.deleteAll()
        withContext(Dispatchers.Main){
            if (result > 0)
                view.showMessage("Deleted all items successfully!")
            else
                view.showMessage("No items added yet!")
        }
    }

    suspend fun deleteItem(mechanicalPart: MechanicalPart){
        val result = mechanicalPartDao.deleteItem(mechanicalPart)
        withContext(Dispatchers.Main){
            view.showMessage("Deleted successfully!")
        }
    }
}