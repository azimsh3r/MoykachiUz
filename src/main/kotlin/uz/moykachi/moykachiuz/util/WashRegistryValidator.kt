package uz.moykachi.moykachiuz.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import uz.moykachi.moykachiuz.dto.WashRegistryDTO
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.service.AutoService
import uz.moykachi.moykachiuz.service.UserService
import uz.moykachi.moykachiuz.service.WashRegistryService

@Component
class WashRegistryValidator @Autowired constructor(private val userService: UserService, private val autoService: AutoService, private val registryService: WashRegistryService) : Validator {
    override fun supports(clazz: Class<*>): Boolean = true

    override fun validate(target: Any, errors: Errors) {
        val workflow : WashRegistryDTO = target as WashRegistryDTO
        if (userService.findAllById(workflow.userId ?: return).isEmpty()) {
            errors.rejectValue("userId", "", "User with this data is not found!")
        } else if (autoService.findAllById(workflow.autoId ?: return).isEmpty()) {
            errors.rejectValue("autoId", "", "Auto with this data is not found!")
        } else if (registryService.isBusy(workflow.userId!!)) {
            errors.rejectValue("userId", "", "User is busy!")
        }
    }
}
