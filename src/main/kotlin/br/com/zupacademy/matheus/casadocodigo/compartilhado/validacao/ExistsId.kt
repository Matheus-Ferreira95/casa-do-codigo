package br.com.zupacademy.matheus.casadocodigo.compartilhado.validacao

import javax.persistence.EntityManager
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsIdValidator::class])
annotation class ExistsId(
    val message: String = "Id não existente",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
    val targetClass: KClass<*>,
)

class ExistsIdValidator(val manager: EntityManager) : ConstraintValidator<ExistsId, Long> {

    private lateinit var targetClass: KClass<*>

    override fun initialize(constraintAnnotation: ExistsId) {
        targetClass = constraintAnnotation.targetClass
    }

    override fun isValid(value: Long?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) { // se for null é por que o id não era obrigatório.
            return true
        }

        return manager.createQuery("SELECT 1 FROM ${targetClass.simpleName} t WHERE t.id =:value")
            .setParameter("value", value)
            .resultList
            .isNotEmpty()
    }
}