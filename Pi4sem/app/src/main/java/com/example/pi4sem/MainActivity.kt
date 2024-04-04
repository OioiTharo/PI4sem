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
        val cadastre = findViewById<TextView>(R.id.cadastre)
        btnEntrar.setOnClickListener() {

            val intent = Intent(this, tela_produtos::class.java)
            startActivity(intent)
        }
        cadastre.setOnClickListener() {
            val intent = Intent(this, tela_cadastro::class.java )
            startActivity(intent)
        }

    }

}