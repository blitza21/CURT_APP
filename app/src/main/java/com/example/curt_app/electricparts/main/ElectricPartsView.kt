package com.example.curt_app.electricparts.main

import com.example.curt_app.model.ElectricPart

interface ElectricPartsView {

    fun showData(electricPartsList : List<ElectricPart>)
    fun showMessage(msg : String)
}