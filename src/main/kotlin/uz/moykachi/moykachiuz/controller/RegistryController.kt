package uz.moykachi.moykachiuz.controller

import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uz.moykachi.moykachiuz.dto.WashRegistryDTO
import uz.moykachi.moykachiuz.exception.ExceptionResponse
import uz.moykachi.moykachiuz.exception.InvalidDataException
import uz.moykachi.moykachiuz.exception.SuccessResponse
import uz.moykachi.moykachiuz.models.AutoInstance
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.util.ValidationExceptionHandler
import uz.moykachi.moykachiuz.models.WashRegistry
import uz.moykachi.moykachiuz.service.WashRegistryService
import uz.moykachi.moykachiuz.util.WashRegistryValidator

@RestController
@RequestMapping("/registry")
class RegistryController @Autowired  constructor (val modelMapper: ModelMapper, val washRegistryService: WashRegistryService, val validationExceptionHandler: ValidationExceptionHandler, val washRegistryValidator: WashRegistryValidator) {
    @PostMapping
    fun registerCarWash(@Valid @RequestBody washRegistryDTO: WashRegistryDTO, bindingResult: BindingResult) : ResponseEntity<out Any> {
        washRegistryValidator.validate(washRegistryDTO, bindingResult)

        if (bindingResult.hasFieldErrors()) {
            return validationExceptionHandler.handleValidationException(bindingResult)
        }

        washRegistryService.save( WashRegistry().apply {
            user = User().apply { id = washRegistryDTO.userId!! }
            auto = AutoInstance().apply { id = washRegistryDTO.autoId!! }
        } )

        return ResponseEntity(SuccessResponse(), HttpStatus.OK)
    }

    @PostMapping("/finishWash")
    fun finishWash(@RequestParam("userId", required = false) userId : Int?) : ResponseEntity<out Any> {
        try {
            washRegistryService.finishWash(userId ?: return ResponseEntity(ExceptionResponse("userId param must be provided"), HttpStatus.BAD_REQUEST))
        } catch (e: RuntimeException) {
            return ResponseEntity(ExceptionResponse(e.message!!), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(SuccessResponse("Successful!"), HttpStatus.OK)
    }
}
