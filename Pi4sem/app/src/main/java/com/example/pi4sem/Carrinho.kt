package com.example.pi4sem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Carrinho : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalTextView: TextView
    private lateinit var goToPaymentButton: Button
    private var total: Double = 0.0
    private lateinit var cartAdapter: CartAdapter
    private var items: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)

        recyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.totalTextView)
        goToPaymentButton = findViewById(R.id.goToPaymentButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchCartItems()


        goToPaymentButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java).apply {
                var value=updateTotal()
                putExtra("TOTAL", total.toString())
                putExtra("USER", userId)
                putParcelableArrayListExtra("PRODUCT_LIST", ArrayList(items))
            }
            startActivity(intent)
        }
    }

    private fun fetchCartItems() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://951c7ccc-dfe0-45e1-9cb8-a93dbb2f287f-00-2vz8o7pqwf4y2.janeway.repl.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CartApiService::class.java)

        val sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", 0)

        api.getCartItems(userId).enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    items = response.body()?.toMutableList() ?: mutableListOf()
                    setupAdapter()
                    updateTotal()
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                // Tratamento de falhas
            }
        })
    }

    private fun setupAdapter() {
        cartAdapter = CartAdapter(items, this) { updateTotal() }
        recyclerView.adapter = cartAdapter
    }

    fun updateTotal() {
        total = items.sumOf { (it.produtoPreco?.toDouble() ?: 0.0) * it.quantidadeDisponivel.toDouble() }
        runOnUiThread {
            totalTextView.text = "Total: R$${String.format("%.2f", total)}"
        }
    }
}
