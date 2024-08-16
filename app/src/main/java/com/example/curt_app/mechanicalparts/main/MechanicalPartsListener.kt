package com.example.curt_app.mechanicalparts.main

import com.example.curt_app.model.MechanicalPart

interface MechanicalPartsListener {
    fun deleteItem(mechanicalPart: MechanicalPart)
    fun editItem(mechanicalPart: MechanicalPart)
}