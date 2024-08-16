package com.example.curt_app.categories

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.curt_app.R
import com.example.curt_app.model.db.InventoryDatabase
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CategoriesFragment : Fragment() , CategoriesView {
    lateinit var mcv1 : MaterialCardView
    lateinit var mcv2 : MaterialCardView
    lateinit var mcv3 : MaterialCardView
    lateinit var tvElectricCount : TextView
    lateinit var tvMechanicalCount : TextView
    lateinit var tvRawMaterialCount : TextView
    lateinit var categoriesPresenter: CategoriesPresenter
    lateinit var btnSignOut : Button
    val auth : FirebaseAuth = Firebase.auth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setupPresenter()
        handleBackButton()
        lifecycleScope.launch(Dispatchers.IO){
            categoriesPresenter.getElectricCount()
            categoriesPresenter.getMechanicalCount()
            categoriesPresenter.getRawMaterialCount()
        }
        mcv1.setOnClickListener{
            findNavController().navigate(R.id.categories_to_electricParts)
        }
        mcv2.setOnClickListener{
            findNavController().navigate(R.id.categories_to_mechanicalParts)
        }
        mcv3.setOnClickListener {
            findNavController().navigate(R.id.categories_to_rawMaterials)
        }
        btnSignOut.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.categories_to_login)
            Toast.makeText(activity , "Successfully signed out" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI(view : View){
        mcv1 = view.findViewById(R.id.mcv1)
        mcv2 = view.findViewById(R.id.mcv2)
        mcv3 = view.findViewById(R.id.mcv3)
        tvElectricCount = view.findViewById(R.id.tvElectricCount)
        tvMechanicalCount = view.findViewById(R.id.tvMechanicalCount)
        tvRawMaterialCount = view.findViewById(R.id.tvRawMaterialCount)
        btnSignOut = view.findViewById(R.id.btnSignOut)
    }

    private fun setupPresenter(){
        val electricPartDao = InventoryDatabase.getInstance(activity as Context).getElectricPartDao()
        val mechanicalPartDao = InventoryDatabase.getInstance(activity as Context).getMechanicalPartDao()
        val rawMaterialDao = InventoryDatabase.getInstance(activity as Context).getRawMaterialDao()
        categoriesPresenter = CategoriesPresenter(this , electricPartDao , mechanicalPartDao , rawMaterialDao)
    }

    override fun showElectricCount(listSize: Int) {
        if (listSize == 1)
            tvElectricCount.text = "${listSize.toString()} item"
        else
            tvElectricCount.text = "${listSize.toString()} items"
    }

    override fun showMechanicalCount(listSize: Int) {
        if (listSize == 1)
            tvMechanicalCount.text = "${listSize.toString()} item"
        else
            tvMechanicalCount.text = "${listSize.toString()} items"
    }

    override fun showRawMaterialCount(listSize: Int) {
        if (listSize == 1)
            tvRawMaterialCount.text = "${listSize.toString()} item"
        else
            tvRawMaterialCount.text = "${listSize.toString()} items"
    }

    private fun handleBackButton(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

}