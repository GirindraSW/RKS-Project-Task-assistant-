package com.girindra.android.presensionline.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.girindra.android.presensionline.R
import com.girindra.android.presensionline.dashboard.DashboardActivity
import com.girindra.android.presensionline.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onRestart() {
        super.onRestart()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.username.setText("")
        binding.username.setText("")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            when {
                binding.username.text.isEmpty() -> binding.username.error = "Please Input"
                binding.password.text.isEmpty() -> binding.password.error = "Please Input"
                else -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra("name", binding.username.text.toString())
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Do you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            this.finishAffinity()
        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->
        }
        alertDialogBuilder.show()
    }
}