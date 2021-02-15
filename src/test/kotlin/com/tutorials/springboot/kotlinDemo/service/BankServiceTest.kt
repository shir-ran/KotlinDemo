package com.tutorials.springboot.kotlinDemo.service

import com.tutorials.springboot.kotlinDemo.dataSource.BankDataSource
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
        val banks = bankService.getBanks()
        
        // then
        verify{ dataSource.retrieveBanks()}
        
    }
}