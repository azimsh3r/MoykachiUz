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
import org.springframework.web.bind.annotation.RestController
import uz.moykachi.moykachiuz.dto.WorkflowDTO
import uz.moykachi.moykachiuz.exception.SuccessResponse
import uz.moykachi.moykachiuz.models.AutoInstance
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.util.ValidationExceptionHandler
import uz.moykachi.moykachiuz.models.WashRegistry
import uz.moykachi.moykachiuz.service.WashRegistryService
import uz.moykachi.moykachiuz.util.WorkflowValidator

@RestController
@RequestMapping("/workflow")
class WorkflowController @Autowired  constructor (val modelMapper: ModelMapper, val washRegistryService: WashRegistryService, val validationExceptionHandler: ValidationExceptionHandler, val workflowValidator: WorkflowValidator) {
    @PostMapping
    fun registerCarWash(@Valid @RequestBody workflowDTO: WorkflowDTO, bindingResult: BindingResult) : ResponseEntity<out Any> {
        workflowValidator.validate(workflowDTO, bindingResult)

        if (bindingResult.hasFieldErrors()) {
            return validationExceptionHandler.handleValidationException(bindingResult)
        }

        washRegistryService.save( WashRegistry().apply {
            user = User().apply { id = workflowDTO.userId!! }
            auto = AutoInstance().apply { id = workflowDTO.autoId!! }
        } )

        return ResponseEntity(SuccessResponse(), HttpStatus.OK)
    }
}
