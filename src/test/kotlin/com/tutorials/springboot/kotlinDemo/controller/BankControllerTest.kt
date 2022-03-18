package com.tutorials.springboot.kotlinDemo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tutorials.springboot.kotlinDemo.model.Bank
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/banks"
    val accountNumberUnchanged = "12-655-125859"
    val accountNumberChanged = "12-655-125566"



    @Nested
    @DisplayName("get /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all banks`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo{print()}
                .andExpect{
                    status{ isOk()}
                    content{contentType(MediaType.APPLICATION_JSON)}
                    jsonPath("$", hasSize<Array<Any>>(4))
                    jsonPath("$[0].accountNumber"){value("12-655-125478")}
                }
        }

        //@Test
        fun `should return no content when list of banks is empty`(){
            // when/then
            mockMvc.get(baseUrl)
                .andDo{print()}
                .andExpect{
                    status{ isNoContent()}
                }

        }
    }

    @Nested
    @DisplayName("get /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank{
        @Test
        fun `should return the bank with the given account number`() {

            // when/ then
            mockMvc.get("$baseUrl/$accountNumberUnchanged")
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
    @DisplayName("post /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add new bank account`() {
            // given
            val newBank = Bank("new_bank_account", 34.415, 2)
            
            // when
            val performPost = mockMvc.post(baseUrl){
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
            val newBank = Bank(accountNumberUnchanged, 34.415, 2)

            // when
            val performPost = mockMvc.post(baseUrl){
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

    @Nested
    @DisplayName("patch /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBank{
        @Test
        @DirtiesContext
        fun `should update existing bank`() {
            // given
            val bank = Bank(accountNumberChanged, 72.6, 1)

            // when/then
            mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
                .andDo{print()}
                .andExpect{
                    status{ isOk()}
                    content{contentType(MediaType.APPLICATION_JSON)}
                    jsonPath("$.accountNumber"){value(accountNumberChanged)}
                    jsonPath("$.trust"){value(72.6)}
                    jsonPath("$.transactionFee"){value(1)}
                }

            mockMvc.get("$baseUrl/${bank.accountNumber}")
                .andExpect {
                    status { isOk() }
                    content { content { json(objectMapper.writeValueAsString(bank))} }
                }
        }

        @Test
        fun `should return error when bank for update does not exist`(){
            // given
            val bank = Bank("not_found", 72.6, 1)

            // when/then
            mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }
                .andDo{print()}
                .andExpect{
                    status{ isNotFound()}
                }

        }
    }

    @Nested
    @DisplayName("delete /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {
        @Test
        fun `should delete existing bank`() {

            // when/then
            mockMvc.delete("$baseUrl/$accountNumberChanged")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }
            // make sure the bank was deleted
            mockMvc.get("$baseUrl/${accountNumberChanged}")
                .andExpect {
                    status { isNotFound() }
                }
            // re-add the bank for isolated testing
            val newBank = Bank(accountNumberChanged, 0.8,6)

            // when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
        }

        @Test
        fun `should fail to delete non existing bank`() {
            //given
            val accountNumber = "not_found"

            // when/ then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }
    

}