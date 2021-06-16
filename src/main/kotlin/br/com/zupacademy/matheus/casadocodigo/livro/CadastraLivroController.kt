package br.com.zupacademy.matheus.casadocodigo.livro

import br.com.zupacademy.matheus.casadocodigo.autor.Autor
import br.com.zupacademy.matheus.casadocodigo.categoria.Categoria
import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.ExistsId
import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.UniqueValue
import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.validator.constraints.Length
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDate

import javax.persistence.EntityManager
import javax.validation.Valid
import javax.validation.constraints.Future
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/livros")
class CadastraLivroController(val manager: EntityManager) {

    private val log: Logger = LoggerFactory.getLogger(CadastraLivroController::class.java)

    @Transactional(rollbackFor = [Exception::class])
    @PostMapping
    fun cadastrar(@RequestBody @Valid request: novoLivroRequest): ResponseEntity<Any> {
        request.paraLivro(manager)
            .let{ manager.persist(it) }
            .also { log.info("cadastrando um novo livro") }
            .let { return ResponseEntity.ok(it) }
    }
}

data class novoLivroRequest(
    @field:NotBlank
    @field:UniqueValue(message = "Titulo já cadastrado", fieldName = "titulo", targetClass = Livro::class)
    val titulo: String?,

    @field:NotBlank
    @field:Length(max = 500)
    val resumo: String?,

    @field:NotBlank
    val sumario: String?,

    @field:NotNull
    @field:Min(20)
    val preco: BigDecimal?,

    @field:NotNull
    @field:Min(100)
    val paginas: Int?,

    @field:NotBlank
    @field:UniqueValue(message = "Isbn já existente", fieldName = "isbn", targetClass = Livro::class)
    val isbn: String?,

    @field:NotNull
    @field:Future
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    val dataLancamento: LocalDate?,

    @field:NotNull
    @field:ExistsId(targetClass = Categoria::class)
    val categoriaId: Long?,

    @field:NotNull
    @field:ExistsId(targetClass = Autor::class)
    val autorId: Long?
) {

    fun paraLivro(manager: EntityManager): Livro = Livro(
        titulo = titulo!!,
        resumo = resumo!!,
        sumario = sumario!!,
        preco = preco!!,
        paginas = paginas!!,
        isbn = isbn!!,
        dataLancamento = dataLancamento!!,
        categoria = manager.find(Categoria::class.java, categoriaId),
        autor = manager.find(Autor::class.java, autorId)
    )
}