package br.com.zupacademy.matheus.casadocodigo.cliente

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository : JpaRepository<Cliente, Long> {

}
