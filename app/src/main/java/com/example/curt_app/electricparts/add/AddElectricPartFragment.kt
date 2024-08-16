package com.example.curt_app.electricparts.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.curt_app.R
import com.example.curt_app.model.db.InventoryDatabase
import com.example.curt_app.model.ElectricPart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddElectricPartFragment : Fragment() , AddElectricPartView {
    lateinit var etName : EditText
    lateinit var etVoltage : EditText
    lateinit var etCurrent : EditText
    lateinit var etPower : EditText
    lateinit var etQuantity : EditText
    lateinit var fabSave : FloatingActionButton
    lateinit var addElectricPartPresenter : AddElectricPartPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_electric_part, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        fabSave.setOnClickListener {
            val name = etName.text.toString()
            val voltage = etVoltage.text.toString()
            val current = etCurrent.text.toString()
            val power = etPower.text.toString()
            val quantity = etQuantity.text.toString()
            if (inputCheck(name , voltage , current, power, quantity)){
                val electricPart = ElectricPart(name , quantity.toInt() ,voltage.toDouble() , current.toDouble() , power.toDouble())
                lifecycleScope.launch(Dispatchers.IO){
                    addElectricPartPresenter.add(electricPart)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.addElectricPart_to_electricParts)
                    }
                }
            }
            else{
                showMessage("One or more input fields missing!")
            }
        }
    }

    private fun initUI(view : View){
        etName = view.findViewById(R.id.etName)
        etVoltage = view.findViewById(R.id.etVoltage)
        etCurrent = view.findViewById(R.id.etCurrent)
        etPower = view.findViewById(R.id.etPower)
        etQuantity = view.findViewById(R.id.etQuantity)
        fabSave = view.findViewById(R.id.fabSave)
    }

    private fun setupPresenter(){
        val electricPartDao = InventoryDatabase.getInstance(activity as Context).getElectricPartDao()
        addElectricPartPresenter = AddElectricPartPresenter(this , electricPartDao)
    }

    private fun inputCheck(name : String, voltage: String, current : String , power : String , quantity : String)  : Boolean{
        return (name.isNotBlank() && voltage.toDoubleOrNull() != null  && current.toDoubleOrNull() != null && power.toDoubleOrNull() != null && quantity.toIntOrNull() != null)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }

}