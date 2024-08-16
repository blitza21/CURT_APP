package com.example.curt_app.rawmaterials.main

import com.example.curt_app.model.RawMaterial

interface RawMaterialsListener {
    fun deleteItem(rawMaterial: RawMaterial)
    fun editItem(rawMaterial: RawMaterial)
}