package com.example.pi4sem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val inputEmail = findViewById<EditText>(R.id.email)
        val inputSenha = findViewById<EditText>(R.id.senha)

        btnEntrar.setOnClickListener() {
            var Email = inputEmail.getText().toString()

            val intent = Intent(this, tela_produtos::class.java)
            intent.putExtra("usuario", Email)
            startActivity(intent)
        }
    }

}