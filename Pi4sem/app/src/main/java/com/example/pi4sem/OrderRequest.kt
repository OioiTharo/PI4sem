package com.example.pi4sem
data class OrderRequest(
    val userId: Int,
    val total: Double,
    val products: List<Produto>
)
