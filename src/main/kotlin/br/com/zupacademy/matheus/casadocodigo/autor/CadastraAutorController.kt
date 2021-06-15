package br.com.zupacademy.matheus.casadocodigo.autor

import org.hibernate.validator.constraints.Length
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/autores")
class CadastraAutorController(private val autorRepository: AutorRepository) {

    private val log: Logger = LoggerFactory.getLogger(CadastraAutorController::class.java)

    @PostMapping
    fun cadastrar(@RequestBody @Valid request: NovoAutorRequest): ResponseEntity<Any> {

        if (autorRepository.existsByEmail(request.email!!)) {
            log.info("Não foi possível realizar o cadastro, email {} já existente", request.email)
            return ResponseEntity.unprocessableEntity().body("Email já cadastrado")
        }
        
        request.paraAutor()
            .let (autorRepository::save)
            .also { log.info("Cadastrando um novo autor") }
        return ResponseEntity.ok().build()
    }
}


data class NovoAutorRequest(
    @field:NotBlank
    val nome: String?,

    @field:NotBlank
    @field:Email
    val email: String?,

    @field:NotBlank
    @field:Length(max = 400)
    val descricao: String?,
) {
    fun paraAutor(): Autor = Autor(nome = nome!!, email = email!!, descricao = descricao!!)
}