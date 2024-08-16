package com.example.curt_app.electricparts.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.curt_app.R
import com.example.curt_app.model.db.InventoryDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditElectricPartFragment : Fragment() , EditElectricPartView {
    lateinit var etName : EditText
    lateinit var etVoltage : EditText
    lateinit var etCurrent : EditText
    lateinit var etPower : EditText
    lateinit var etQuantity : EditText
    lateinit var fabSave : FloatingActionButton
    lateinit var editElectricPartPresenter: EditElectricPartPresenter
    val args : EditElectricPartFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_electric_part, container, false)
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
                args.electricPart.name = name
                args.electricPart.voltage = voltage.toDouble()
                args.electricPart.current = current.toDouble()
                args.electricPart.powerRating = power.toDouble()
                args.electricPart.quantity = quantity.toInt()
                lifecycleScope.launch(Dispatchers.IO){
                    editElectricPartPresenter.update(args.electricPart)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.editElectricPart_to_electricParts)
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
        etName.setText(args.electricPart.name)
        etVoltage = view.findViewById(R.id.etVoltage)
        etVoltage.setText(args.electricPart.voltage.toString())
        etCurrent = view.findViewById(R.id.etCurrent)
        etCurrent.setText(args.electricPart.current.toString())
        etPower = view.findViewById(R.id.etPower)
        etPower.setText(args.electricPart.powerRating.toString())
        etQuantity = view.findViewById(R.id.etQuantity)
        etQuantity.setText(args.electricPart.quantity.toString())
        fabSave = view.findViewById(R.id.fabSave)
    }

    private fun setupPresenter(){
        val electricPartDao = InventoryDatabase.getInstance(activity as Context).getElectricPartDao()
        editElectricPartPresenter = EditElectricPartPresenter(this , electricPartDao)
    }

    private fun inputCheck(name : String, voltage: String, current : String , power : String , quantity : String)  : Boolean{
        return (name.isNotBlank() && voltage.toDoubleOrNull() != null  && current.toDoubleOrNull() != null && power.toDoubleOrNull() != null && quantity.toIntOrNull() != null)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }


}