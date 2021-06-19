package br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao

import br.com.zupacademy.matheus.casadocodigo.cliente.ClienteRequest
import br.com.zupacademy.matheus.casadocodigo.estado.EstadoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class VerificaSePaisPossuiEstadoValidator : Validator {

    @Autowired
    lateinit var estadoRepository: EstadoRepository

    override fun supports(clazz: Class<*>): Boolean {
        return ClienteRequest::class.java == clazz
    }

    override fun validate(o: Any, errors: Errors) {
        if (errors.hasErrors()) {
            return
        }

        val request = o as ClienteRequest
        if (estadoRepository.existsByPaisId(request.paisId!!) && request.estadoId == null) {
            errors.rejectValue("estadoId", "", "Como este pais possui estado, o estado é obrigatório")
        }
    }
}
