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
import uz.moykachi.moykachiuz.dto.WorkRegistryDTO
import uz.moykachi.moykachiuz.exception.ExceptionResponse
import uz.moykachi.moykachiuz.exception.SuccessResponse
import uz.moykachi.moykachiuz.models.AutoInstance
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.util.ValidationExceptionHandler
import uz.moykachi.moykachiuz.models.WorkRegistry
import uz.moykachi.moykachiuz.service.WorkRegistryRepository
import uz.moykachi.moykachiuz.util.WorkRegistryValidator

@RestController
@RequestMapping("/registry")
class WorkRegistryController @Autowired  constructor (val modelMapper: ModelMapper, val workRegistryRepository: WorkRegistryRepository, val validationExceptionHandler: ValidationExceptionHandler, val workRegistryValidator: WorkRegistryValidator) {
    @PostMapping
    fun registerCarWash(@Valid @RequestBody workRegistryDTO: WorkRegistryDTO, bindingResult: BindingResult) : ResponseEntity<out Any> {
        workRegistryValidator.validate(workRegistryDTO, bindingResult)

        if (bindingResult.hasFieldErrors()) {
            return validationExceptionHandler.handleValidationException(bindingResult)
        }

        workRegistryRepository.save( WorkRegistry().apply {
            user = User().apply { id = workRegistryDTO.userId!! }
            auto = AutoInstance().apply { id = workRegistryDTO.autoId!! }
        } )

        return ResponseEntity(SuccessResponse(), HttpStatus.OK)
    }

    @PostMapping("/finishWash")
    fun finishWash(@RequestParam("userId", required = false) userId : Int?) : ResponseEntity<out Any> {
        try {
            workRegistryRepository.finishWash(userId ?: return ResponseEntity(ExceptionResponse("userId param must be provided"), HttpStatus.BAD_REQUEST))
        } catch (e: RuntimeException) {
            return ResponseEntity(ExceptionResponse(e.message!!), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(SuccessResponse("Successful!"), HttpStatus.OK)
    }
}
