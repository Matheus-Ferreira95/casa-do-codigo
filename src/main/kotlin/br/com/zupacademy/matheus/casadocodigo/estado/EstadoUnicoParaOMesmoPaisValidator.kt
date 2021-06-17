package br.com.zupacademy.matheus.casadocodigo.estado

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class EstadoUnicoParaOMesmoPaisValidator : Validator {

    @Autowired
    lateinit var estadoRepository: EstadoRepository

    override fun supports(clazz: Class<*>): Boolean {
        return NovoEstadoRequest::class.java == clazz
    }

    override fun validate(o: Any, errors: Errors) {
       if (errors.hasErrors()) {
           return
       }

        val request = o as NovoEstadoRequest

        estadoRepository.findByNomeAndPaisId(request.nome, request.idPais)
            .ifPresent { errors.rejectValue("nome", "", "Neste pais já existe um estado com este nome") }
    }
}