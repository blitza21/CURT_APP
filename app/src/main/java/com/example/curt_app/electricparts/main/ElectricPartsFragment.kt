package com.example.curt_app.electricparts.main

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curt_app.R
import com.example.curt_app.model.db.InventoryDatabase
import com.example.curt_app.model.ElectricPart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ElectricPartsFragment : Fragment() , ElectricPartsView, ElectricPartsListener {
    lateinit var rv : RecyclerView
    lateinit var electricPartsAdapter: ElectricPartsAdapter
    lateinit var fabAdd : FloatingActionButton
    lateinit var fabDeleteAll : FloatingActionButton
    lateinit var electricPartsPresenter: ElectricPartsPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electric_parts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        lifecycleScope.launch(Dispatchers.IO){
            electricPartsPresenter.getAll()
        }
        fabDeleteAll.setOnClickListener{
            showAlertDialog()
        }
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.electricParts_to_addElectricPart)
        }
    }

    private fun initUI(view : View){
        rv = view.findViewById(R.id.rv)
        fabAdd = view.findViewById(R.id.fabAdd)
        fabDeleteAll = view.findViewById(R.id.fabDeleteAll)
        electricPartsAdapter = ElectricPartsAdapter(listOf() , this)
        rv.adapter = electricPartsAdapter
        rv.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupPresenter(){
        val electricPartDao = InventoryDatabase.getInstance(activity as Context).getElectricPartDao()
        electricPartsPresenter = ElectricPartsPresenter(this , electricPartDao)
    }

    override fun showData(electricPartsList: List<ElectricPart>) {
        electricPartsAdapter.electricPartsList = electricPartsList
        electricPartsAdapter.notifyDataSetChanged()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }

    override fun deleteItem(electricPart: ElectricPart) {
        lifecycleScope.launch(Dispatchers.IO) {
            electricPartsPresenter.deleteItem(electricPart)
            electricPartsPresenter.getAll()
        }

    }

    override fun editItem(electricPart: ElectricPart) {
        val action = ElectricPartsFragmentDirections.electricPartsToEditElectricPart(electricPart)
        findNavController().navigate(action)
    }

    private fun showAlertDialog(){
        val alertDialog = AlertDialog.Builder(activity as Context)
        alertDialog.setTitle("Alert!!")
            .setMessage("Are you sure you want to delete all electric parts?")
            .setIcon(R.drawable.ic_warning)
            .setCancelable(false)
            .setNegativeButton("No" , DialogInterface.OnClickListener{dialogInterface, i -> dialogInterface.cancel() })
            .setPositiveButton("Yes" , DialogInterface.OnClickListener {dialogInterface, i ->
                lifecycleScope.launch(Dispatchers.IO){
                    electricPartsPresenter.deleteAll()
                    electricPartsPresenter.getAll()
                }
            })
            .create()
            .show()
    }
}