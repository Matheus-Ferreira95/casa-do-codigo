package br.com.zupacademy.matheus.casadocodigo.pais

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaisRepository : JpaRepository<Pais, Long> {
}