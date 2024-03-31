package com.example.pi4sem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val inputEmail = findViewById<EditText>(R.id.email)
        val inputSenha = findViewById<EditText>(R.id.senha)
        val cadastre = findViewById<TextView>(R.id.cadastre)
        btnEntrar.setOnClickListener() {
            var Email = inputEmail.getText().toString()

            val intent = Intent(this, tela_produtos::class.java)
            intent.putExtra("usuario", Email)
            startActivity(intent)
        }
        cadastre.setOnClickListener() {
            val intent = Intent(this, tela_cadastro::class.java )
            startActivity(intent)
        }

    }

}