package com.example.pet_feeding.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.pet_feeding.MainActivity
import com.example.pet_feeding.R
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class SignUpFragment : Fragment() {
    private lateinit var authentication: FirebaseAuth
    private lateinit var emailInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var conPasswordInput: TextInputLayout
    private lateinit var usernameInput: TextInputLayout
    private lateinit var loading: CircularProgressIndicator
    private lateinit var signInNav: FrameLayout
    private lateinit var signUpBtn: ImageButton
    private lateinit var documentReference: DocumentReference
    private lateinit var fStore:FirebaseFirestore
    private lateinit var uid:String
    private lateinit var fAuth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        authentication = Firebase.auth
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInNav = view.findViewById(R.id.sign_in_nav)
        signUpBtn = view.findViewById(R.id.sign_up_btn)
        usernameInput = view.findViewById(R.id.sign_up_username)
        emailInput = view.findViewById(R.id.sign_up_email)
        passwordInput = view.findViewById(R.id.sign_up_password)
        conPasswordInput = view.findViewById(R.id.sign_up_con_password)
        loading = view.findViewById(R.id.loading)
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance()




        signInNav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view, SignInFragment())
                addToBackStack("auth")
            }
        }

        usernameInput.editText?.addTextChangedListener {
            usernameInput.error = null
            usernameInput.isErrorEnabled = false
        }
        passwordInput.editText?.addTextChangedListener {
            passwordInput.error = null
            passwordInput.isErrorEnabled = false
            conPasswordInput.error = null
            conPasswordInput.isErrorEnabled = false
        }
        conPasswordInput.editText?.addTextChangedListener {
            conPasswordInput.error = null
            conPasswordInput.isErrorEnabled = false
        }
        emailInput.editText?.addTextChangedListener {
            emailInput.error = null
            emailInput.isErrorEnabled = false
        }

        signUpBtn.setOnClickListener {

            val checkPassword =
                conPasswordInput.editText?.text.toString() == passwordInput.editText?.text.toString()
            val checkPasswordLen = passwordInput.editText?.text.toString().length < 6
            val checkConPasswordLen = conPasswordInput.editText?.text.toString().length < 6
            val checkEmail = emailInput.editText?.text.toString()
                .matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))

            if (passwordInput.editText?.text.toString() == ""){
                passwordInput.error = "Password is empty"
            }
            else if (checkPasswordLen) {
                passwordInput.error = "Password must more than 6 digit"
            }

            if (conPasswordInput.editText?.text.toString() == ""){
                conPasswordInput.error = "Confirm password is empty"
            }
            else if (checkConPasswordLen) {
                conPasswordInput.error = "Confirm Password must more than 6 digit"
            }

            if (emailInput.editText?.text.toString() == ""){
                emailInput.error = "Email is empty"
            }
            else if (!checkEmail) {
                emailInput.error = "Email invalid"
            }

            if(usernameInput.editText?.text.toString().isEmpty()){
                usernameInput.error = "Username is empty"
            }else if (!usernameInput.editText?.text.toString().matches(Regex("\\w+"))){
                usernameInput.error = "Username is alphanumeric only"
            }

            if (!checkPassword && !checkPasswordLen && !checkConPasswordLen) {
                conPasswordInput.error = "Password and Confirm password not match"
            }

            if (!emailInput.isErrorEnabled && !passwordInput.isErrorEnabled && !conPasswordInput.isErrorEnabled && !usernameInput.isErrorEnabled) {
                signUpBtn.visibility = View.GONE
                loading.visibility = View.VISIBLE
                createAccount(
                    emailInput.editText?.text.toString(),
                    passwordInput.editText?.text.toString()
                )
            }
        }
    }
    private fun saveNameFireStore(username: String){
        uid = fAuth.currentUser?.uid.toString()
        documentReference = fStore.collection("users").document(uid)
        val user: MutableMap <String , Any> = HashMap()
        user["username"] = username
        documentReference.set(user)
    }

    private fun createAccount(email: String, password: String) {
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    saveNameFireStore(usernameInput.editText?.text.toString())
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    emailInput.error = task.exception?.message.toString()
                }
                loading.visibility = View.GONE
                signUpBtn.visibility = View.VISIBLE
            }
    }



}