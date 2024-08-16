package com.example.curt_app.categories

import com.example.curt_app.model.db.ElectricPartDao
import com.example.curt_app.model.db.MechanicalPartDao
import com.example.curt_app.model.db.RawMaterialDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesPresenter(
    private val view : CategoriesView,
    private val electricPartDao: ElectricPartDao,
    private val mechanicalPartDao: MechanicalPartDao,
    private val rawMaterialDao: RawMaterialDao
) {

    suspend fun getElectricCount(){
        val electricCount = electricPartDao.getAll().size
        withContext(Dispatchers.Main) {
            view.showElectricCount(electricCount)
        }
    }

    suspend fun getMechanicalCount(){
        val mechanicalCount = mechanicalPartDao.getAll().size
        withContext(Dispatchers.Main) {
            view.showMechanicalCount(mechanicalCount)
        }
    }

    suspend fun getRawMaterialCount(){
        val rawMaterialCount = rawMaterialDao.getAll().size
        withContext(Dispatchers.Main) {
            view.showRawMaterialCount(rawMaterialCount)
        }
    }
}