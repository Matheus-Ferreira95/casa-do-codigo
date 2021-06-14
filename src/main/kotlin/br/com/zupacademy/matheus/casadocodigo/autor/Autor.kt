package br.com.zupacademy.matheus.casadocodigo.autor

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Autor(val nome: String, val email: String, val descricao: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    lateinit var criadoEm: LocalDateTime
}