package br.com.zupacademy.matheus.casadocodigo.categoria

import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "uk_nome", columnNames = ["nome"])])
class Categoria(
    @Column(nullable = false, unique = true)
    val nome: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}