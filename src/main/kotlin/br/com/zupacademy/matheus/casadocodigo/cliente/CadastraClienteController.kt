package br.com.zupacademy.matheus.casadocodigo.cliente

import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.CPFOuCNPJ
import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.ExistsId
import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.UniqueValue
import br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao.VerificaSePaisPossuiEstadoValidator
import br.com.zupacademy.matheus.casadocodigo.estado.Estado
import br.com.zupacademy.matheus.casadocodigo.pais.Pais
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.persistence.EntityManager
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/clientes")
class CadastraClienteController(
    val manager: EntityManager,
    val validator: VerificaSePaisPossuiEstadoValidator,
    val clienteRepository: ClienteRepository,
) {

    @InitBinder
    fun init(binder: WebDataBinder) = binder.addValidators(validator)

    @Transactional(rollbackFor = [Exception::class])
    @PostMapping
    fun cadastrar(@RequestBody @Valid request: ClienteRequest, uri: UriComponentsBuilder): ResponseEntity<Any> {
        return request.paraCliente(manager)
            .let { clienteRepository.save(it) }
            .let { ResponseEntity.created(uri.path("/clientes/${it.id}").build().toUri()).build() }
    }

}

data class ClienteRequest(
    @field:NotBlank
    @field:Email
    @field:UniqueValue(message = "Email já cadastrado", fieldName = "email", targetClass = Cliente::class)
    val email: String?,

    @field:NotBlank
    val nome: String?,

    @field:NotBlank
    val sobrenome: String?,

    @field:NotBlank
    @field:CPFOuCNPJ
    @field:UniqueValue(message = "Documento já cadastrado", fieldName = "documento", targetClass = Cliente::class)
    val documento: String?,

    @field:NotBlank
    val endereco: String?,

    @field:NotBlank
    val complemento: String?,

    @field:NotBlank
    val cidade: String?,

    @field:NotNull
    @field:ExistsId(message = "pais não existente", targetClass = Pais::class)
    val paisId: Long?,

    @field:ExistsId(message = "estado não existente", targetClass = Pais::class)
    val estadoId: Long?,

    @field:NotBlank
    val telefone: String?,

    @field:NotBlank
    val cep: String?,
) {

    fun paraCliente(manager: EntityManager): Cliente {
        return Cliente(
            email = email!!,
            nome = nome!!,
            sobrenome = sobrenome!!,
            documento = documento!!,
            endereco = endereco!!,
            complemento = complemento!!,
            cidade = cidade!!,
            pais = manager.find(Pais::class.java, paisId),
            telefone = telefone!!,
            cep = cep!!
        ).apply {
            if (estadoId != null)  estado = manager.find(Estado::class.java, estadoId)
        }
    }
}