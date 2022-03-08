package com.tutorials.springboot.kotlinDemo.dataSource

import com.tutorials.springboot.kotlinDemo.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
    fun addBank(bank: Bank): Bank
}