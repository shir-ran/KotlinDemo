package com.tutorials.springboot.kotlinDemo.model

import com.fasterxml.jackson.annotation.JsonProperty


data class Bank(
    @JsonProperty("account_number")
    val accountNumber: String,
    val trust: Double,
    @JsonProperty("default_transaction_fee")
    val transactionFee: Int
    )