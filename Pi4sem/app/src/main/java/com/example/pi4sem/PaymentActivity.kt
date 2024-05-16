package com.example.pi4sem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var totalValue = intent.getStringExtra("TOTAL")?.toDoubleOrNull()
        val userId = intent.getIntExtra("USER", 0)
        val productList = intent.getParcelableArrayListExtra<Produto>("PRODUCT_LIST")

        findViewById<TextView>(R.id.totalValueText).text = totalValue.toString()


        val cardNumberInput: EditText = findViewById(R.id.cardNumberInput)
        val cardExpirationInput: EditText = findViewById(R.id.cardExpirationInput)
        val cardCVCInput: EditText = findViewById(R.id.cardCVCInput)
        val finishPaymentButton: Button = findViewById(R.id.finishPaymentButton)

        finishPaymentButton.setOnClickListener {
            //if (validateCardDetails(cardNumberInput.text.toString(), cardExpirationInput.text.toString(), cardCVCInput.text.toString())) {

            enviaOrdem(userId, totalValue.toString().toDouble(), productList)
            //} else {
            //    Toast.makeText(this, "Detalhes do cartÃ£o invÃ¡lidos", Toast.LENGTH_LONG).show()
            //}
        }
    }

    private fun enviaOrdem(userId: Int, total: Double, products: ArrayList<Produto>?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://9038ecf2-2975-4974-8922-924bf7ea6920-00-im976vx84po1.kirk.repl.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(OrderApiService::class.java)
        val orderRequest = OrderRequest(userId, total, products!!)
        val call = api.createOrder(orderRequest)
        call.enqueue(object : Callback<ResponseCompra> {
            override fun onResponse(call: Call<ResponseCompra>, response: Response<ResponseCompra>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PaymentActivity, "Pedido realizado com sucesso", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@PaymentActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@PaymentActivity, "Erro ao realizar pedido", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseCompra>, t: Throwable) {
                Toast.makeText(this@PaymentActivity, "Falha na conexÃ£o", Toast.LENGTH_LONG).show()
            }
        })
    }

    interface OrderApiService {
        @POST("/")
        fun createOrder(@Body orderRequest: OrderRequest): Call<ResponseCompra>
    }
}

