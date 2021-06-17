package br.com.zupacademy.matheus.casadocodigo.estado

import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.ExistsId
import br.com.zupacademy.matheus.casadocodigo.pais.Pais
import br.com.zupacademy.matheus.casadocodigo.pais.PaisRepository
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/estados")
class CadastraEstadoController(
    val estadoRepository: EstadoRepository,
    val paisRepository: PaisRepository,
    val validator: EstadoUnicoParaOMesmoPaisValidator,
) {

    @InitBinder
    fun init(binder: WebDataBinder) = binder.addValidators(validator)

    @PostMapping
    @Transactional(rollbackFor = [Exception::class])
    fun cadastrar(@RequestBody @Valid request: NovoEstadoRequest): ResponseEntity<Any> {
        return request.paraEstado(paisRepository)
            .let (estadoRepository::save)
            .let { ResponseEntity.ok().build() }
    }
}


data class NovoEstadoRequest(
    @field:NotBlank
    val nome: String?,

    @field:NotNull
    @field:ExistsId(message = "Pais com id informado não encontrado", targetClass = Pais::class)
    val idPais: Long?,
) {

    fun paraEstado(paisRepository: PaisRepository): Estado {
         return Estado(nome = nome!!, pais = paisRepository.findById(idPais!!).get())
    }
}