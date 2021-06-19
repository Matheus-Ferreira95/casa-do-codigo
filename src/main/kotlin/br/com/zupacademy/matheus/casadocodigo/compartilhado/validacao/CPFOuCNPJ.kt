package br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@ConstraintComposition(CompositionType.OR)
@CPF(message = "CPF inválido")
@CNPJ(message = "CNPJ inválido")
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [])
annotation class CPFOuCNPJ(val message: String = "",
                           val groups: Array<KClass<Any>> = [],
                           val payload: Array<KClass<Payload>> = [],)
