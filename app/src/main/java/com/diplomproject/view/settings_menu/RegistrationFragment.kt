package com.diplomproject.view.settings_menu

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.diplomproject.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : BaseFragmentSettingsMenu<FragmentRegistrationBinding>(
    FragmentRegistrationBinding::inflate
)  {

    var auth: FirebaseAuth? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        initClickedViews()
    }

    private fun initClickedViews() {
        binding.apply {
            button.setOnClickListener {
                createUser()
            }
        }
    }


    private fun createUser(){

            val email: String = binding?.editTextTextEmailAddress?.getText().toString()
            val password: String = binding?.editTextNumberPassword?.getText().toString()
           // val password: String = binding?.editTextNumberPassword2?.text

            if (TextUtils.isEmpty(email)) {
                binding?.editTextTextEmailAddress?.setError("Email cannot be empty")
                binding?.editTextTextEmailAddress?.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                binding?.editTextNumberPassword?.setError("Password cannot be empty")
                binding?.editTextNumberPassword?.requestFocus()
            } else if (!password.equals(binding?.editTextNumberPassword2?.text?.toString())) {
                binding?.editTextNumberPassword2?.setError("Passwords must be match!")
                binding?.editTextNumberPassword2?.requestFocus()
            }
            else {
                auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@RegistrationFragment.context,
                            "User registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        //TODO прикрутить переход при успешной авторизации
                       // startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@RegistrationFragment.context,
                            "Registration Error: " + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }



    }

    companion object {
        fun newInstance() = RegistrationFragment()

    }
}