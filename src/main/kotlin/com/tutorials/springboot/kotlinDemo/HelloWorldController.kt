package com.tutorials.springboot.kotlinDemo

import com.tutorials.springboot.kotlinDemo.model.Bank
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    @GetMapping("meir")
    fun helloMeir():String {
        return "hello Meir - this is a rest endpoint!"
        val bank: Bank = Bank("accountNumber string", 5.56, 7)


    }

    @GetMapping("shirran")
    fun helloShir():String {
        return "hello shir-ran - how are you?"
    }

}