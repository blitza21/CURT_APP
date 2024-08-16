package com.example.curt_app.mechanicalparts.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curt_app.R
import com.example.curt_app.model.MechanicalPart

class MechanicalPartsAdapter(var mechanicalPartsList : List<MechanicalPart>, val mechanicalPartsListener: MechanicalPartsListener) : RecyclerView.Adapter<MechanicalPartsAdapter.MechanicalPartsViewHolder>() {
    class MechanicalPartsViewHolder(mechanicalItem : View) : RecyclerView.ViewHolder(mechanicalItem) {
        val tvName : TextView = mechanicalItem.findViewById(R.id.tvName)
        val tvMaterial : TextView = mechanicalItem.findViewById(R.id.tvMaterial)
        val tvDimensions : TextView = mechanicalItem.findViewById(R.id.tvDimensions)
        val tvWeight : TextView = mechanicalItem.findViewById(R.id.tvWeight)
        val tvQuanity : TextView = mechanicalItem.findViewById(R.id.tvQuantity)
        val btnEdit : ImageButton = mechanicalItem.findViewById(R.id.btnEdit)
        val btnDel  : ImageButton = mechanicalItem.findViewById(R.id.btnDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MechanicalPartsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.mechanical_item , parent , false)
        return MechanicalPartsViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return mechanicalPartsList.size
    }

    override fun onBindViewHolder(holder: MechanicalPartsViewHolder, position: Int) {
        holder.tvName.text = mechanicalPartsList[position].name
        holder.tvMaterial.text = "Material: ${mechanicalPartsList[position].material }"
        holder.tvDimensions.text = "Dimensions: ${mechanicalPartsList[position].dimensions }"
        holder.tvWeight.text = "Weight: ${mechanicalPartsList[position].weight } KG"
        holder.tvQuanity.text = "Stock: ${mechanicalPartsList[position].quantity } items"
        holder.btnEdit.setOnClickListener {
            mechanicalPartsListener.editItem(mechanicalPartsList[position])
        }
        holder.btnDel.setOnClickListener {
            mechanicalPartsListener.deleteItem(mechanicalPartsList[position])
        }
    }

}