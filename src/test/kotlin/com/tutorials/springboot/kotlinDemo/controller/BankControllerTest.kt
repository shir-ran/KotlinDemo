package com.tutorials.springboot.kotlinDemo.controller

import ch.qos.logback.core.spi.LifeCycle
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tutorials.springboot.kotlinDemo.model.Bank
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
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    val baseUrl: String = "/api/banks"



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
    
    @Nested
    @DisplayName("post /ai/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add new bank account`() {
            // given
            val newBank = Bank("new_bank_account", 34.415, 2)
            
            // when
            val performPost = mockMvc.post("$baseUrl"){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.accountNumber"){value("new_bank_account")}
                    jsonPath("$.trust"){value(34.415)}
                    jsonPath("$.transactionFee"){value(2)}
                }
        }

        @Test
        fun `should show error if account number already exists`() {
            // given
            // given
            val newBank = Bank("12-655-125859", 34.415, 2)

            // when
            val performPost = mockMvc.post("$baseUrl"){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }

        }
        
        
    }


    

}