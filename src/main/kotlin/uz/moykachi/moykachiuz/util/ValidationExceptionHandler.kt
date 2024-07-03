package uz.moykachi.moykachiuz.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import uz.moykachi.moykachiuz.exception.ExceptionResponse
import java.util.function.Consumer

@Component
class ValidationExceptionHandler {
    fun handleValidationException(bindingResult: BindingResult) : ResponseEntity<out Any> {
        val stringBuilder = StringBuilder()
        val fieldErrorList = bindingResult.fieldErrors
        fieldErrorList.forEach(Consumer { fieldError: FieldError ->
            stringBuilder.append(
                fieldError.defaultMessage
            ).append(" ")
        })
        return ResponseEntity(ExceptionResponse(stringBuilder.toString()), HttpStatus.BAD_REQUEST)
    }
}
