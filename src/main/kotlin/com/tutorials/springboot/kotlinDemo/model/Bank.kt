package com.tutorials.springboot.kotlinDemo.model


data class Bank(
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
    )