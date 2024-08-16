package com.example.curt_app.authentication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.curt_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    lateinit var etEmail : EditText
    lateinit var etPass : EditText
    lateinit var btnRegister : Button
    lateinit var tvSignIn : TextView
    lateinit var progressBar: ProgressBar
    private val auth : FirebaseAuth = Firebase.auth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            if (email.isBlank()) {
                Toast.makeText(activity, "Enter your email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.isBlank()) {
                Toast.makeText(activity, "Enter your password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            registerWithFirebase(email , pass)
        }

        tvSignIn.setOnClickListener {
            findNavController().navigate(R.id.register_to_login)
        }

    }

    private fun initUI(view : View){
        etEmail = view.findViewById(R.id.etEmail)
        etPass = view.findViewById(R.id.etPass)
        btnRegister = view.findViewById(R.id.btnRegister)
        tvSignIn = view.findViewById(R.id.tvSignIn)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun registerWithFirebase(email : String, pass : String){
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(activity,"Your account has been created" , Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.register_to_categories)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(activity,"Invalid Email or Password", Toast.LENGTH_SHORT).show()
                    Log.i("TAG" ,task.exception.toString())
                }
            }
    }
}