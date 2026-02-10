package com.example.gawe_an2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gawe_an2.R
import com.example.gawe_an2.databinding.ActivityLoginBinding
import com.example.gawe_an2.model.User
import com.example.gawe_an2.network.ApiClient
import com.example.gawe_an2.network.ApiResponse
import com.example.gawe_an2.network.HttpMethod
import com.example.gawe_an2.util.LoginMapper
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.etLEmail.text.toString()
        val password = binding.etLPassword.text.toString()

        lifecycleScope.launch {
            val body = JSONObject().apply {
                put("email", email)
                put("password", password)
            }

            val response = ApiClient.request(
                url = "http://10.0.2.2:5000/api/auth",
                method = HttpMethod.POST,
                body = body
            )

            when(response) {
                is ApiResponse.Success -> {
                    val message = response.data.getString("message")
                    val user = LoginMapper.map(response.data)

                    toast(message)
                    saveUser(user)
                }

                is ApiResponse.Error -> {
                    toast(response.message)
                }
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun saveUser(user: User) {
        val pref = getSharedPreferences("AUTH", MODE_PRIVATE)

        pref.edit().apply {
            putInt("id", user.id)
            putString("profilePicture", user.profilePicture)
            putString("fullname", user.fullname)
            putString("email", user.email)
            putString("phoneNumber", user.phoneNumber)
            putString("role", user.role)
        }
    }
}