package com.example.pi4sem

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class tela_cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)



        val entre = findViewById<TextView>(R.id.Entre)
        entre.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
        }

    }
}