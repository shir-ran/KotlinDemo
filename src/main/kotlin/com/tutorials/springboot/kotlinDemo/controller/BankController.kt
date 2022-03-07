package com.tutorials.springboot.kotlinDemo.controller

import com.tutorials.springboot.kotlinDemo.model.Bank
import com.tutorials.springboot.kotlinDemo.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api")
class BankController(
    @Autowired
    private val bankService: BankService
) {
    @ExceptionHandler(NoSuchElementException :: class)
    fun handleNotFound(e:NoSuchElementException): ResponseEntity<String> = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping("banks")
    fun getBanks():Collection<Bank> {
        return bankService.getBanks()


    }

    @GetMapping("banks/{accountNumber}")
    fun getBank(@PathVariable accountNumber : String): Bank {
        return bankService.getBank(accountNumber)
    }

}