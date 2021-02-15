package com.tutorials.springboot.kotlinDemo.controller

import com.tutorials.springboot.kotlinDemo.model.Bank
import com.tutorials.springboot.kotlinDemo.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class BankController(
    @Autowired
    private val bankService: BankService
) {

    @GetMapping("banks")
    fun getBanks():Collection<Bank> {
        return bankService.getBanks()


    }

}