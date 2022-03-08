package com.tutorials.springboot.kotlinDemo.dataSource.mock

import ch.qos.logback.core.spi.LifeCycle
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

internal class MockBankDataSourceTest{

    private val mockBankDataSource = MockBankDataSource()

    @Nested
    @DisplayName("retrieveBanks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class  RetrieveBanks{

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

    @Nested
    @DisplayName("retrieveBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class RetrieveBank {
        @Test
        fun `should retrieve a bank account`() {
            // given
            val accountNumber: String = "12-655-125478"

            // when
            val bank = mockBankDataSource.retrieveBank(accountNumber)

            // then
            assertThat(bank.trust).isEqualTo(0.5)
            assertThat(bank.transactionFee).isEqualTo(1)
        }

        @Test
        fun `should fail to retrieve a non existing account`() {
            // given
            val accountNumber: String = "not_exist"

            // when/then
            assertThrows<NoSuchElementException> { mockBankDataSource.retrieveBank(accountNumber) }

        }

    }

}