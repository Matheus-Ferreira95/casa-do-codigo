package br.com.zupacademy.matheus.casadocodigo.categoria

import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.UniqueValue
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
        request.paraCategoria()
            .let {
                categoriaRepository.save(it)
                return ResponseEntity.ok().build()
            }
    }
}

data class NovaCategoriaRequest(
    @field:NotBlank
    @field:UniqueValue(message = "Já existe uma categoria com este nome", fieldName = "nome", targetClass = Categoria::class)
    val nome: String?
) {
    fun paraCategoria(): Categoria = Categoria(nome = nome!!)
}