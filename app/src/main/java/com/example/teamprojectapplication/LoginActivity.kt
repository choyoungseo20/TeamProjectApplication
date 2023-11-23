package com.example.teamprojectapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.teamprojectapplication.databinding.ActivityLoginBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var binding: ActivityLoginBinding
    val viewModel: PostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            signinAndSignup()
        }
    }

    private fun signinAndSignup() {
        auth?.createUserWithEmailAndPassword(
            binding.txtEmail.text.toString(),
            binding.txtPassword.text.toString()
        )?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                moveMainPage(task.result?.user)
            } else if (task.exception?.message.isNullOrEmpty()) {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            } else {
                signinEmail()
            }
        }
    }

    private fun signinEmail() {
        auth?.signInWithEmailAndPassword(
            binding.txtEmail.text.toString(),
            binding.txtPassword.text.toString()
        )?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.setPost()
                moveMainPage(task.result?.user)
            } else {
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}