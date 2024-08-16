package com.example.curt_app.mechanicalparts.add

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
import com.example.curt_app.R
import com.example.curt_app.model.db.InventoryDatabase
import com.example.curt_app.model.MechanicalPart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMechanicalPartFragment : Fragment()  , AddMechanicalPartView{
    lateinit var etName : EditText
    lateinit var etMaterial : EditText
    lateinit var etDimensions : EditText
    lateinit var etWeight : EditText
    lateinit var etQuantity : EditText
    lateinit var fabSave : FloatingActionButton
    lateinit var addMechanicalPartPresenter : AddMechanicalPartPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_mechanical_part, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        fabSave.setOnClickListener {
            val name = etName.text.toString()
            val material = etMaterial.text.toString()
            val dimensions = etDimensions.text.toString()
            val weight = etWeight.text.toString()
            val quantity = etQuantity.text.toString()
            if (inputCheck(name , material , dimensions, weight, quantity)){
                val mechanicalPart = MechanicalPart(name , quantity.toInt() ,material, dimensions , weight.toDouble())
                lifecycleScope.launch(Dispatchers.IO){
                    addMechanicalPartPresenter.add(mechanicalPart)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.addMechanicalPart_to_mechanicalParts)
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
        etMaterial = view.findViewById(R.id.etMaterial)
        etDimensions = view.findViewById(R.id.etDimensions)
        etWeight = view.findViewById(R.id.etWeight)
        etQuantity = view.findViewById(R.id.etQuantity)
        fabSave = view.findViewById(R.id.fabSave)
    }

    private fun setupPresenter(){
        val mechaincalPartDao = InventoryDatabase.getInstance(activity as Context).getMechanicalPartDao()
        addMechanicalPartPresenter = AddMechanicalPartPresenter(this , mechaincalPartDao)
    }

    private fun inputCheck(name : String, material: String, dimensions : String, weight : String, quantity : String)  : Boolean{
        return (name.isNotBlank() && material.isNotBlank()  && dimensions.isNotBlank() && weight.toDoubleOrNull() != null && quantity.toIntOrNull() != null)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }
}