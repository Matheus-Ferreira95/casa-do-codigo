package br.com.zupacademy.matheus.casadocodigo.livro

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/livros")
class ListaLivroController(val livroRepository: LivroRepository) {

    private val log: Logger = LoggerFactory.getLogger(ListaLivroController::class.java)

    @GetMapping
    fun listar(pageable: Pageable): ResponseEntity<Page<LivroResponse>> {
            livroRepository.findAll(pageable)
            .map(::LivroResponse)
            .also { log.info("Listando todos livros") }
            .let { return ResponseEntity.ok(it) }
    }

    @GetMapping("/{id}")
    fun listarPorId(@PathVariable id: Long): ResponseEntity<DetalhesLivroResponse> {
        return livroRepository.findById(id)
            .map(::DetalhesLivroResponse)
            .map { livro -> ResponseEntity.ok(livro)}
            .orElseGet{
                log.warn("Id informado não encontrado")
                ResponseEntity.notFound().build()
            }
    }
}

class LivroResponse(livro: Livro) {
    val id = livro.id
    val nome = livro.titulo
}

class DetalhesLivroResponse(livro: Livro) {
    val titulo = livro.titulo
    val resumo = livro.resumo
    val sumario = livro.sumario
    val preco = livro.preco
    val paginas = livro.paginas
    val isbn = livro.isbn
    val dataLancamento = livro.dataLancamento
    val categoria = livro.categoria
    val autor = livro.autor
}