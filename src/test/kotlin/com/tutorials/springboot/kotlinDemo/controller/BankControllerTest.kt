package com.tutorials.springboot.kotlinDemo.controller

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest() {

    val baseUrl: String = "/api/banks"

    @Autowired
    lateinit var mockMvc: MockMvc

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all banks`() {
            // when/then
            mockMvc.get("$baseUrl")
                .andDo{print()}
                .andExpect{
                    status{ isOk()}
                    content{contentType(MediaType.APPLICATION_JSON)}
                    jsonPath("$", hasSize<Array<Any>>(4))
                    jsonPath("$[0].accountNumber"){value("12-655-125478")}
                }
        }
    }

    @Nested
    @DisplayName("getBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank{
        @Test
        fun `should return the bank with the given account number`() {
            // given
            val accountNumber = "12-655-125859"

            // when/ then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust"){value(0.7)}
                    jsonPath("$.transactionFee"){value(3)}
                }

        }

        @Test
        fun `should return empty answer`() {
            // given
            val accountNumber = "does_not_exist"

            // when/ then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }


    

}