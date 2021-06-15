package br.com.zupacademy.matheus.casadocodigo.categoria

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/categorias")
class CadastraCategoriaController(val categoriaRepository: CategoriaRepository) {

    @PostMapping
    @Transactional(rollbackOn = [ Exception::class ])
    fun cadastra(@RequestBody @Valid request: NovaCategoriaRequest): ResponseEntity<Any> {
        if (categoriaRepository.existsByNome(request.nome)) {
            return ResponseEntity.badRequest().body("Já existe uma categoria com nome ${request.nome}")
        }
        request.paraCategoria()
            .let {
                categoriaRepository.save(it)
                return ResponseEntity.ok().build()
            }
    }
}

data class NovaCategoriaRequest(
    @field:NotBlank val nome: String?
) {
    fun paraCategoria(): Categoria = Categoria(nome = nome!!)
}