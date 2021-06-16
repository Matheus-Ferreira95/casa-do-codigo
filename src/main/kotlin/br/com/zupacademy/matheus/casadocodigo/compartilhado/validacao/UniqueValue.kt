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
@Constraint(validatedBy = [UniqueValueValidator::class])
annotation class UniqueValue(
    val message: String = "Valor já existente",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
    val fieldName: String,
    val targetClass: KClass<*>
)

class UniqueValueValidator(val manager: EntityManager): ConstraintValidator<UniqueValue, Any> {

    private lateinit var fieldName: String
    private lateinit var targetClass: KClass<*>

    override fun initialize(constraintAnnotation: UniqueValue) {
        fieldName = constraintAnnotation.fieldName
        targetClass = constraintAnnotation.targetClass
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        return manager
            .createQuery("select 1 from ${targetClass.simpleName} where $fieldName =: value")
            .setParameter("value", value)
            .resultList
            .isEmpty()
    }
}