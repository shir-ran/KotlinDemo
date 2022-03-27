package com.tutorials.springboot.kotlinDemo.dataSource.network

import com.tutorials.springboot.kotlinDemo.dataSource.BankDataSource
import com.tutorials.springboot.kotlinDemo.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.io.IOException

@Repository("network")
class NetworkDataSource(@Autowired private val restTemplate: RestTemplate) :
    BankDataSource  {

    override fun retrieveBanks(): Collection<Bank> {
        //val respType = object: ParameterizedTypeReference<BankList>(){}
        val response = restTemplate.getForEntity("http://54.193.31.159/banks", BankList::class.java)

        return response.body?.results?: throw IOException("Could not fetch banks from the network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun addBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }


}