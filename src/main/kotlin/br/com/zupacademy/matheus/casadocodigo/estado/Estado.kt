package br.com.zupacademy.matheus.casadocodigo.estado

import br.com.zupacademy.matheus.casadocodigo.pais.Pais
import javax.persistence.*

@Entity
class Estado(
    val nome: String,
    @ManyToOne
    val pais: Pais
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}