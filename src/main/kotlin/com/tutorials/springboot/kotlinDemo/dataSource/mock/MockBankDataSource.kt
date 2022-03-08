package com.tutorials.springboot.kotlinDemo.dataSource.mock

import com.tutorials.springboot.kotlinDemo.dataSource.BankDataSource
import com.tutorials.springboot.kotlinDemo.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class MockBankDataSource : BankDataSource {

    val banks = mutableListOf(
        Bank("12-655-125478", 0.5,1),
        Bank("12-655-125859", 0.7,3),
        Bank("12-655-125839", 0.7,3),
        Bank("12-655-125566", 0.8,6)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

//    override fun retrieveBank(accountNumber: String): Bank? {
//        for (current in banks){
//            if (current.accountNumber == accountNumber)
//                return current
//        }
//
//        return null
//    }

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank account with number $accountNumber")

    override fun addBank(bank: Bank): Bank {
        if (banks.any{it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        banks.add(bank)
        return bank
    }
}


