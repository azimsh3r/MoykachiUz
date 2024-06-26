package uz.moykachi.moykachiuz.controller

import uz.moykachi.moykachiuz.dto.AutoDTO
import uz.moykachi.moykachiuz.exception.ExceptionResponse
import uz.moykachi.moykachiuz.exception.SuccessResponse
import uz.moykachi.moykachiuz.service.AutoService
import uz.moykachi.moykachiuz.util.AutoValidator
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.function.Consumer

@RestController
@RequestMapping("/auto")
class AutoController (@Autowired val autoService: AutoService, @Autowired val autoValidator: AutoValidator){

    @GetMapping
    fun getAll() = autoService.findAll()

    @PostMapping("/register")
    fun register (@Valid @RequestBody autoDTO : AutoDTO, bindingResult: BindingResult) : ResponseEntity<out Any> {
        autoValidator.validate(autoDTO, bindingResult)

        if(bindingResult.hasErrors()) {
            val stringBuilder = StringBuilder()
            val fieldErrorList = bindingResult.fieldErrors
            fieldErrorList.forEach(Consumer { fieldError: FieldError ->
                stringBuilder.append(
                    fieldError.defaultMessage
                ).append(" ")
            })
            return ResponseEntity(ExceptionResponse(stringBuilder.toString()), HttpStatus.BAD_REQUEST)
        }

        autoService.save(autoDTO)
        return ResponseEntity(SuccessResponse("Auto Registered Successfully!"), HttpStatus.OK)
    }
}
