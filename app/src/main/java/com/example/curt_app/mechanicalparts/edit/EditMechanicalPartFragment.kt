package com.example.curt_app.mechanicalparts.edit

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

class EditMechanicalPartFragment : Fragment() , EditMechanicalPartView {
    lateinit var etName : EditText
    lateinit var etMaterial : EditText
    lateinit var etDimensions : EditText
    lateinit var etWeight : EditText
    lateinit var etQuantity : EditText
    lateinit var fabSave : FloatingActionButton
    lateinit var editMechanicalPartPresenter : EditMechanicalPartPresenter
    val args : EditMechanicalPartFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_mechanical_part, container, false)
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
                args.mechanicalPart.name = name
                args.mechanicalPart.material = material
                args.mechanicalPart.dimensions = dimensions
                args.mechanicalPart.weight = weight.toDouble()
                args.mechanicalPart.quantity = quantity.toInt()
                lifecycleScope.launch(Dispatchers.IO){
                    editMechanicalPartPresenter.update(args.mechanicalPart)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.editMechanicalPart_to_mechanicalParts)
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
        etName.setText(args.mechanicalPart.name)
        etMaterial = view.findViewById(R.id.etMaterial)
        etMaterial.setText(args.mechanicalPart.material)
        etDimensions = view.findViewById(R.id.etDimensions)
        etDimensions.setText(args.mechanicalPart.dimensions)
        etWeight = view.findViewById(R.id.etWeight)
        etWeight.setText(args.mechanicalPart.weight.toString())
        etQuantity = view.findViewById(R.id.etQuantity)
        etQuantity.setText(args.mechanicalPart.quantity.toString())
        fabSave = view.findViewById(R.id.fabSave)
    }

    private fun setupPresenter(){
        val mechaincalPartDao = InventoryDatabase.getInstance(activity as Context).getMechanicalPartDao()
        editMechanicalPartPresenter = EditMechanicalPartPresenter(this , mechaincalPartDao)
    }
    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }
    private fun inputCheck(name : String, material: String, dimensions : String, weight : String, quantity : String)  : Boolean{
        return (name.isNotBlank() && material.isNotBlank()  && dimensions.isNotBlank() && weight.toDoubleOrNull() != null && quantity.toIntOrNull() != null)
    }

}