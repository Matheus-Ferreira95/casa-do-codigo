package br.com.zupacademy.matheus.casadocodigo.cliente

import br.com.zupacademy.matheus.casadocodigo.estado.Estado
import br.com.zupacademy.matheus.casadocodigo.pais.Pais
import javax.persistence.*

@Entity
class Cliente(
    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false)
    val sobrenome: String,

    @Column(unique = true, nullable = false)
    val documento: String,

    @Column(nullable = false)
    val endereco: String,

    @Column(nullable = false)
    val complemento: String,

    @Column(nullable = false)
    val cidade: String,

    @ManyToOne(optional = false)
    val pais: Pais,

    @Column(nullable = false)
    val telefone: String,

    @Column(nullable = false)
    val cep: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    var estado: Estado? = null
}