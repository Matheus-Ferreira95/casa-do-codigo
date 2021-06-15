package br.com.zupacademy.matheus.casadocodigo.categoria

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoriaRepository : JpaRepository<Categoria, Long> {
    fun existsByNome(nome: String?): Boolean
}