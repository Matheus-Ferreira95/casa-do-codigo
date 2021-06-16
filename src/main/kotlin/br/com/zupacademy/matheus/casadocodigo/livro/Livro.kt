package br.com.zupacademy.matheus.casadocodigo.livro

import br.com.zupacademy.matheus.casadocodigo.autor.Autor
import br.com.zupacademy.matheus.casadocodigo.categoria.Categoria
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
class Livro(
    @Column(unique = true, nullable = false)
    val titulo: String,

    @Column(nullable = false)
    val resumo: String,

    @Column(nullable = false)
    val sumario: String,

    @Column(nullable = false)
    val preco: BigDecimal,

    @Column( nullable = false)
    val paginas: Int,

    @Column(unique = true, nullable = false)
    val isbn: String,

    @Column(nullable = false)
    val dataLancamento: LocalDate,

    @ManyToOne
    val categoria: Categoria,

    @ManyToOne
    val autor: Autor
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}