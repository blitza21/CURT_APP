package com.example.curt_app.rawmaterials.edit

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
import com.example.curt_app.model.RawMaterial
import com.example.curt_app.rawmaterials.add.AddRawMaterialPresenter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditRawMaterialFragment : Fragment() , EditRawMaterialView {
    lateinit var etName : EditText
    lateinit var etType : EditText
    lateinit var etPurity : EditText
    lateinit var etQuantity : EditText
    lateinit var fabSave : FloatingActionButton
    lateinit var editRawMaterialPresenter: EditRawMaterialPresenter
    val args : EditRawMaterialFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_raw_material, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        fabSave.setOnClickListener {
            val name = etName.text.toString()
            val type = etType.text.toString()
            val purity = etPurity.text.toString()
            val quantity = etQuantity.text.toString()
            if (inputCheck(name , type , purity, quantity)){
                args.rawMaterial.name = name
                args.rawMaterial.type = type
                args.rawMaterial.purity = purity.toDouble()
                args.rawMaterial.quantity = quantity.toInt()
                lifecycleScope.launch(Dispatchers.IO){
                    editRawMaterialPresenter.update(args.rawMaterial)
                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.editRawMaterial_to_rawMaterials)
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
        etName.setText(args.rawMaterial.name)
        etType = view.findViewById(R.id.etType)
        etType.setText(args.rawMaterial.type)
        etPurity = view.findViewById(R.id.etPurity)
        etPurity.setText(args.rawMaterial.purity.toString())
        etQuantity = view.findViewById(R.id.etQuantity)
        etQuantity.setText(args.rawMaterial.quantity.toString())
        fabSave = view.findViewById(R.id.fabSave)
    }

    private fun setupPresenter(){
        val rawMaterialDao = InventoryDatabase.getInstance(activity as Context).getRawMaterialDao()
        editRawMaterialPresenter = EditRawMaterialPresenter(this , rawMaterialDao)
    }

    private fun inputCheck(name : String, type: String, purity : String , quantity : String)  : Boolean{
        return (name.isNotBlank() && type.isNotBlank() && purity.toDoubleOrNull() != null  && quantity.toIntOrNull() != null)
    }
    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }

}