package com.tutorials.springboot.kotlinDemo.service

import com.tutorials.springboot.kotlinDemo.dataSource.BankDataSource
import com.tutorials.springboot.kotlinDemo.model.Bank
import org.springframework.stereotype.Service
@Service
class BankService(private val bankDataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = bankDataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = bankDataSource.retrieveBank(accountNumber)
    fun addNewBank(bank: Bank): Bank  = bankDataSource.addBank(bank)
    fun updateBank(bank: Bank): Bank = bankDataSource.updateBank(bank)
    fun deleteBank(accountNumber: String) : Unit = bankDataSource.deleteBank(accountNumber)


}

