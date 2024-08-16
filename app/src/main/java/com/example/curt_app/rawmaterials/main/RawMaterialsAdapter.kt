package com.example.curt_app.rawmaterials.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curt_app.R
import com.example.curt_app.model.RawMaterial

class RawMaterialsAdapter(var rawMaterialsList : List<RawMaterial> , val rawMaterialsListener: RawMaterialsListener) : RecyclerView.Adapter<RawMaterialsAdapter.RawMaterialsViewHolder>() {
    class RawMaterialsViewHolder(rawMaterialItem : View) : RecyclerView.ViewHolder(rawMaterialItem) {
        val tvName : TextView = rawMaterialItem.findViewById(R.id.tvName)
        val tvType : TextView = rawMaterialItem.findViewById(R.id.tvType)
        val tvPurity : TextView = rawMaterialItem.findViewById(R.id.tvPurity)
        val tvQuanity : TextView = rawMaterialItem.findViewById(R.id.tvQuantity)
        val btnEdit : ImageButton = rawMaterialItem.findViewById(R.id.btnEdit)
        val btnDel  : ImageButton = rawMaterialItem.findViewById(R.id.btnDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RawMaterialsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.raw_material_item , parent , false)
        return RawMaterialsViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return rawMaterialsList.size
    }

    override fun onBindViewHolder(holder: RawMaterialsViewHolder, position: Int) {
        holder.tvName.text = rawMaterialsList[position].name
        holder.tvType.text = "Type: ${rawMaterialsList[position].type}"
        holder.tvPurity.text = "Purity: ${rawMaterialsList[position].purity } %"
        holder.tvQuanity.text = "Stock: ${rawMaterialsList[position].quantity } items"
        holder.btnEdit.setOnClickListener {
            rawMaterialsListener.editItem(rawMaterialsList[position])
        }
        holder.btnDel.setOnClickListener {
            rawMaterialsListener.deleteItem(rawMaterialsList[position])
        }
    }

}