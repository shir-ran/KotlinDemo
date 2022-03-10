package com.tutorials.springboot.kotlinDemo.dataSource.mock

import ch.qos.logback.core.spi.LifeCycle
import com.tutorials.springboot.kotlinDemo.model.Bank
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

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
            val accountNumber = "12-655-125478"

            // when
            val bank = mockBankDataSource.retrieveBank(accountNumber)

            // then
            assertThat(bank.trust).isEqualTo(0.5)
            assertThat(bank.transactionFee).isEqualTo(1)
        }

        @Test
        fun `should fail to retrieve a non existing account`() {
            // given
            val accountNumber = "not_exist"

            // when/then
            assertThrows<NoSuchElementException> { mockBankDataSource.retrieveBank(accountNumber) }

        }

    }

    @Nested
    @DisplayName("updateBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBank {


        @Test
        fun `should update an existing bank`() {
            //given
            val bank = Bank("12-655-125566", 72.6, 1)

            // when
            val actualBank = mockBankDataSource.updateBank(bank)

            // then
            assertEquals(bank, actualBank)
        }

        @Test
        fun `should fail to update non existing bank`() {
            //given
            val bank = Bank("not_found", 72.6, 1)

            // when
            assertThrows<NoSuchElementException> {
                mockBankDataSource.updateBank(bank)
            }
        }

    }

}