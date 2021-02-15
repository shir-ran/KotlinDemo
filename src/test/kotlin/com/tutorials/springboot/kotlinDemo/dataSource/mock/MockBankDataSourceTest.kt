package com.tutorials.springboot.kotlinDemo.dataSource.mock

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{

    private val mockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a list of banks`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThan(2)
        assertThat(banks.map { it -> it.accountNumber }).doesNotHaveDuplicates()

    }

    @Test
    fun `should provide some mock data`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).allMatch { it.transactionFee  > 0 }

    }

}