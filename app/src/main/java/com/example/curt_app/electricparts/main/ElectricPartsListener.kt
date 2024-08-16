package com.example.curt_app.electricparts.main

import com.example.curt_app.model.ElectricPart

interface ElectricPartsListener {
    fun deleteItem(electricPart: ElectricPart)
    fun editItem(electricPart: ElectricPart)
}