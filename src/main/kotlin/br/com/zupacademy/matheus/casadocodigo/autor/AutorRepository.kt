package br.com.zupacademy.matheus.casadocodigo.autor

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AutorRepository : JpaRepository<Autor, Long>