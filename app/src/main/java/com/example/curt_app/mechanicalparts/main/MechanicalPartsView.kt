package com.example.curt_app.mechanicalparts.main

import com.example.curt_app.model.MechanicalPart

interface MechanicalPartsView {
    fun showData(mechanicalPartsList : List<MechanicalPart>)
    fun showMessage(msg : String)
}