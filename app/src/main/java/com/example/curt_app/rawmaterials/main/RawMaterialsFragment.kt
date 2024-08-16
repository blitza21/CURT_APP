package com.example.curt_app.rawmaterials.main

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curt_app.R
import com.example.curt_app.model.db.InventoryDatabase
import com.example.curt_app.electricparts.main.ElectricPartsFragmentDirections
import com.example.curt_app.model.ElectricPart
import com.example.curt_app.model.RawMaterial
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RawMaterialsFragment : Fragment() , RawMaterialsView, RawMaterialsListener {
    lateinit var rv : RecyclerView
    lateinit var rawMaterialsAdapter: RawMaterialsAdapter
    lateinit var fabAdd : FloatingActionButton
    lateinit var fabDeleteAll : FloatingActionButton
    lateinit var rawMaterialsPresenter: RawMaterialsPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_raw_materials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        lifecycleScope.launch(Dispatchers.IO){
            rawMaterialsPresenter.getAll()
        }
        fabDeleteAll.setOnClickListener{
            showAlertDialog()
        }
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.rawMaterials_to_addRawMaterial)
        }
    }

    private fun initUI(view : View){
        rv = view.findViewById(R.id.rv)
        fabAdd = view.findViewById(R.id.fabAdd)
        fabDeleteAll = view.findViewById(R.id.fabDeleteAll)
        rawMaterialsAdapter = RawMaterialsAdapter(listOf() , this)
        rv.adapter = rawMaterialsAdapter
        rv.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupPresenter(){
        val rawMaterialsDao = InventoryDatabase.getInstance(activity as Context).getRawMaterialDao()
        rawMaterialsPresenter = RawMaterialsPresenter(this , rawMaterialsDao)
    }

    override fun showData(rawMaterialsList: List<RawMaterial>) {
        rawMaterialsAdapter.rawMaterialsList = rawMaterialsList
        rawMaterialsAdapter.notifyDataSetChanged()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }

    override fun deleteItem(rawMaterial: RawMaterial) {
        lifecycleScope.launch(Dispatchers.IO) {
            rawMaterialsPresenter.deleteItem(rawMaterial)
            rawMaterialsPresenter.getAll()
        }

    }

    override fun editItem(rawMaterial: RawMaterial) {
        val action = RawMaterialsFragmentDirections.rawMaterialsToEditRawMaterial(rawMaterial)
        findNavController().navigate(action)
    }

    private fun showAlertDialog(){
        val alertDialog = AlertDialog.Builder(activity as Context)
        alertDialog.setTitle("Alert!!")
            .setMessage("Are you sure you want to delete all raw materials?")
            .setIcon(R.drawable.ic_warning)
            .setCancelable(false)
            .setNegativeButton("No" , DialogInterface.OnClickListener{dialogInterface, i -> dialogInterface.cancel() })
            .setPositiveButton("Yes" , DialogInterface.OnClickListener { dialogInterface, i ->
                lifecycleScope.launch(Dispatchers.IO){
                    rawMaterialsPresenter.deleteAll()
                    rawMaterialsPresenter.getAll()
                }
            })
            .create()
            .show()
    }
}