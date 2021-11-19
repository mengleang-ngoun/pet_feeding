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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.pet_feeding.R
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {
    private lateinit var forgetPasswordNav: FrameLayout
    private lateinit var signUpNav: FrameLayout
    private lateinit var signInBtn: ImageButton
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var errorTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forgetPasswordNav = view.findViewById(R.id.forget_password_nav)
        signUpNav = view.findViewById(R.id.sign_up_nav)

        signInBtn = view.findViewById(R.id.sign_in_btn)
        circularProgressIndicator = view.findViewById(R.id.loading)
        emailInputLayout = view.findViewById(R.id.sign_in_email)
        passwordInputLayout = view.findViewById(R.id.sign_in_password)

        errorTextView = view.findViewById(R.id.text_error)

        passwordInputLayout.editText?.addTextChangedListener {
            passwordInputLayout.error = null
            passwordInputLayout.isErrorEnabled = false
            emailInputLayout.error = null
            emailInputLayout.isErrorEnabled = false
            errorTextView.text = null
            errorTextView.visibility = View.GONE
        }

        emailInputLayout.editText?.addTextChangedListener {
            emailInputLayout.error = null
            emailInputLayout.isErrorEnabled = false
            passwordInputLayout.error = null
            passwordInputLayout.isErrorEnabled = false
            errorTextView.text = null
            errorTextView.visibility = View.GONE
        }

        signInBtn.setOnClickListener {
            if (emailInputLayout.editText?.text.toString().isEmpty()) {
                emailInputLayout.error = "Email is empty"
            } else if (!emailInputLayout.editText?.text.toString()
                    .matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
            ) {
                emailInputLayout.error = "Email invalid"
            }

            if (passwordInputLayout.editText?.text.toString().isEmpty()) {
                passwordInputLayout.error = "Password is empty"
            } else if (passwordInputLayout.editText?.text.toString().length < 6) {
                passwordInputLayout.error = "Password must more than 6 digit"
            }

            if (!emailInputLayout.isErrorEnabled && !passwordInputLayout.isErrorEnabled){
                circularProgressIndicator.visibility = View.VISIBLE
                signInBtn.visibility = View.GONE
                Firebase.auth.signInWithEmailAndPassword(
                    emailInputLayout.editText?.text.toString(),
                    passwordInputLayout.editText?.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Log.d("test", "onViewCreated: ${Firebase.auth.currentUser?.uid}")
                    }else{
                        errorTextView.text = "Wrong email or password"
                        emailInputLayout.error = " "
                        passwordInputLayout.error = " "
                        errorTextView.visibility = View.VISIBLE
                    }
                    circularProgressIndicator.visibility = View.GONE
                    signInBtn.visibility = View.VISIBLE
                }
            }

        }

        forgetPasswordNav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view, ForgotPasswordFragment())
                addToBackStack("auth")
            }
        }
        signUpNav.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.authentication_fragment_container_view, SignUpFragment())
                addToBackStack("auth")
            }
        }
    }
}