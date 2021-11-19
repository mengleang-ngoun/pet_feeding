package com.example.pet_feeding.ui.auth

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
import com.example.pet_feeding.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class SignUpFragment : Fragment() {
    private lateinit var authentication: FirebaseAuth
    private lateinit var emailInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var conPasswordInput: TextInputLayout
    private lateinit var usernameInput: TextInputLayout

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
        val signInNav = view.findViewById<FrameLayout>(R.id.sign_in_nav)
        val signUpBtn = view.findViewById<ImageButton>(R.id.sign_up_btn)

        usernameInput = view.findViewById(R.id.sign_up_username)
        emailInput = view.findViewById(R.id.sign_up_email)
        passwordInput = view.findViewById(R.id.sign_up_password)
        conPasswordInput = view.findViewById(R.id.sign_up_con_password)

        signInNav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view, SignInFragment())
                addToBackStack("auth")
            }
        }

        passwordInput.editText?.addTextChangedListener {
            passwordInput.error = null
            passwordInput.isErrorEnabled = false
        }
        conPasswordInput.editText?.addTextChangedListener {
            conPasswordInput.error = null
            conPasswordInput.isErrorEnabled = false
        }
        emailInput.editText?.addTextChangedListener{
            emailInput.error = null
            emailInput.isErrorEnabled = false
        }

        signUpBtn.setOnClickListener {

            val checkPassword =  conPasswordInput.editText?.text.toString() == passwordInput.editText?.text.toString()
            val checkPasswordLen =  passwordInput.editText?.text.toString().length < 6
            val checkConPasswordLen =  conPasswordInput.editText?.text.toString().length < 6
            val checkEmail = emailInput.editText?.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))

            if (checkPasswordLen){
                passwordInput.error = "Password must more than 6 digit"
            }
            if (checkConPasswordLen){
                conPasswordInput.error = "Confirm Password must more than 6 digit"
            }

            if (!checkPassword && !checkPasswordLen && !checkConPasswordLen) {
                conPasswordInput.error = "Password and Confirm password not match"
            }

            if (!checkEmail){
                emailInput.error = "Email invalid"
            }

            if (checkEmail && checkPassword && !checkConPasswordLen && !checkPasswordLen){
                conPasswordInput.error = null
                conPasswordInput.isErrorEnabled = false
                createAccount(
                    emailInput.editText?.text.toString(),
                    passwordInput.editText?.text.toString()
                )
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        authentication.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("test", "createUserWithEmail:success")
                } else {
                    emailInput.error = "Email is token"
                }
            }
    }

}