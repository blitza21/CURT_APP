package com.example.curt_app.electricparts.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curt_app.R
import com.example.curt_app.model.ElectricPart

class ElectricPartsAdapter(var electricPartsList : List<ElectricPart> , val electricPartsListener: ElectricPartsListener) : RecyclerView.Adapter<ElectricPartsAdapter.ElectricPartsViewHolder>() {
    class ElectricPartsViewHolder(electricItem : View) : RecyclerView.ViewHolder(electricItem) {
        val tvName : TextView = electricItem.findViewById(R.id.tvName)
        val tvVoltage : TextView = electricItem.findViewById(R.id.tvVoltage)
        val tvCurrent : TextView = electricItem.findViewById(R.id.tvCurrent)
        val tvPower : TextView = electricItem.findViewById(R.id.tvPower)
        val tvQuanity : TextView = electricItem.findViewById(R.id.tvQuantity)
        val btnEdit : ImageButton = electricItem.findViewById(R.id.btnEdit)
        val btnDel  : ImageButton = electricItem.findViewById(R.id.btnDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectricPartsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.electric_item , parent , false)
        return ElectricPartsViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return electricPartsList.size
    }

    override fun onBindViewHolder(holder: ElectricPartsViewHolder, position: Int) {
        holder.tvName.text = electricPartsList[position].name
        holder.tvVoltage.text = "Voltage: ${electricPartsList[position].voltage } V"
        holder.tvCurrent.text = "Current: ${electricPartsList[position].current } A"
        holder.tvPower.text = "Power: ${electricPartsList[position].powerRating } W"
        holder.tvQuanity.text = "Stock: ${electricPartsList[position].quantity } items"
        holder.btnEdit.setOnClickListener {
            electricPartsListener.editItem(electricPartsList[position])
        }
        holder.btnDel.setOnClickListener {
            electricPartsListener.deleteItem(electricPartsList[position])
        }
    }

}