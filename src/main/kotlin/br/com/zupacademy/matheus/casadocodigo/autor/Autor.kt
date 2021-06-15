package br.com.zupacademy.matheus.casadocodigo.autor

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "uk_email", columnNames = ["email"])])
class Autor(

    @Column(nullable = false)
    val nome: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val descricao: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @CreationTimestamp
    @Column(nullable = false)
    lateinit var criadoEm: LocalDateTime
}
