package br.com.zupacademy.matheus.casadocodigo.pais

import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.UniqueValue
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/paises")
class CadastraPaisController(val paisRepository: PaisRepository) {

    @Transactional(rollbackFor = [Exception::class])
    @PostMapping
    fun cadastra(@RequestBody @Valid request: NovoPaisRequest): ResponseEntity<Any> {
        return request.paraPais()
            .let (paisRepository::save)
            .let { ResponseEntity.ok().build()}
    }
}

data class NovoPaisRequest(
    @field:NotBlank @field:UniqueValue(message = "Já existe um pais com este nome", fieldName = "nome", targetClass = Pais::class)
    val nome: String?
) {

    fun paraPais(): Pais = Pais(nome = nome!!)
}