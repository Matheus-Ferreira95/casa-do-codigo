package br.com.zupacademy.matheus.casadocodigo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(enableDefaultTransactions = false)
@SpringBootApplication
class CasaDoCodigoApplication

fun main(args: Array<String>) {
	runApplication<CasaDoCodigoApplication>(*args)
}
