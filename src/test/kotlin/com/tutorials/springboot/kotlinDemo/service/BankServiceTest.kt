package com.tutorials.springboot.kotlinDemo.service

import com.tutorials.springboot.kotlinDemo.dataSource.BankDataSource
import com.tutorials.springboot.kotlinDemo.model.Bank
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest{

    private val dataSource: BankDataSource = mockk(relaxed = true)

    private val bankService = BankService(dataSource)


    @Test
    fun `should call the data source to retrieve banks`() {
        //given
        //every { dataSource.retrieveBanks()} returns emptyList()

        // when
        bankService.getBanks()
        
        // then
        verify(exactly = 1){ dataSource.retrieveBanks()}
        
    }

    @Test
    fun `should update an existing bank`() {
        //given
        val bank = Bank("12-655-125566", 72.6, 1)

        // when
        bankService.updateBank(bank)

        // then
        verify(exactly = 1){ dataSource.updateBank(bank)}
    }



}