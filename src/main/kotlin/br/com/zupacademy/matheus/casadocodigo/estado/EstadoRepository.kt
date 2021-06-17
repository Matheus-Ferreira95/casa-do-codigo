package br.com.zupacademy.matheus.casadocodigo.estado

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EstadoRepository : JpaRepository<Estado, Long>{

    fun findByNomeAndPaisId(nome: String?, idPais: Long?): Optional<Estado>
}
