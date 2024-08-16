package com.example.curt_app.rawmaterials.main

import com.example.curt_app.model.RawMaterial

interface RawMaterialsView {
    fun showData(rawMaterialsList : List<RawMaterial>)
    fun showMessage(msg : String)
}