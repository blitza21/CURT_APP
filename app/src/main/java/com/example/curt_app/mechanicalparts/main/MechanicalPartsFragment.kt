package com.example.curt_app.mechanicalparts.main

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
import com.example.curt_app.model.MechanicalPart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MechanicalPartsFragment : Fragment() , MechanicalPartsListener , MechanicalPartsView {
    lateinit var rv : RecyclerView
    lateinit var mechanicalPartsAdapter: MechanicalPartsAdapter
    lateinit var fabAdd : FloatingActionButton
    lateinit var fabDeleteAll : FloatingActionButton
    lateinit var mechanicalPartsPresenter: MechanicalPartsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mechanical_parts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        lifecycleScope.launch(Dispatchers.IO){
            mechanicalPartsPresenter.getAll()
        }
        fabDeleteAll.setOnClickListener{
            showAlertDialog()
        }
        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.mechanicalParts_to_addMechanicalPart)
        }
    }

    private fun initUI(view : View){
        rv = view.findViewById(R.id.rv)
        fabAdd = view.findViewById(R.id.fabAdd)
        fabDeleteAll = view.findViewById(R.id.fabDeleteAll)
        mechanicalPartsAdapter = MechanicalPartsAdapter(listOf() , this)
        rv.adapter = mechanicalPartsAdapter
        rv.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupPresenter(){
        val mechanicalPartDao = InventoryDatabase.getInstance(activity as Context).getMechanicalPartDao()
        mechanicalPartsPresenter = MechanicalPartsPresenter(this , mechanicalPartDao)
    }

    override fun showData(mechanicalPartsList: List<MechanicalPart>) {
        mechanicalPartsAdapter.mechanicalPartsList = mechanicalPartsList
        mechanicalPartsAdapter.notifyDataSetChanged()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(activity , msg , Toast.LENGTH_SHORT).show()
    }

    override fun deleteItem(mechanicalPart: MechanicalPart) {
        lifecycleScope.launch(Dispatchers.IO) {
            mechanicalPartsPresenter.deleteItem(mechanicalPart)
            mechanicalPartsPresenter.getAll()
        }
    }

    override fun editItem(mechanicalPart: MechanicalPart) {
        val action = MechanicalPartsFragmentDirections.mechanicalPartsToEditMechanicalParts(mechanicalPart)
        findNavController().navigate(action)
    }

    private fun showAlertDialog(){
        val alertDialog = AlertDialog.Builder(activity as Context)
        alertDialog.setTitle("Alert!!")
            .setMessage("Are you sure you want to delete all mechanical parts?")
            .setIcon(R.drawable.ic_warning)
            .setCancelable(false)
            .setNegativeButton("No" , DialogInterface.OnClickListener{ dialogInterface, i -> dialogInterface.cancel() })
            .setPositiveButton("Yes" , DialogInterface.OnClickListener { dialogInterface, i ->
                lifecycleScope.launch(Dispatchers.IO){
                    mechanicalPartsPresenter.deleteAll()
                    mechanicalPartsPresenter.getAll()
                }
            })
            .create()
            .show()
    }

}