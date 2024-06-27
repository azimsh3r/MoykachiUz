package uz.moykachi.moykachiuz.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import uz.moykachi.moykachiuz.dto.WorkflowDTO
import uz.moykachi.moykachiuz.service.AutoService
import uz.moykachi.moykachiuz.service.UserService

@Component
class WorkflowValidator @Autowired constructor(private val userService: UserService, private val autoService: AutoService) : Validator {
    override fun supports(clazz: Class<*>): Boolean = true

    override fun validate(target: Any, errors: Errors) {
        val workflow : WorkflowDTO = target as WorkflowDTO
        if (userService.findAllById(workflow.userId ?: return).isEmpty()) {
            errors.rejectValue("userId", "", "User with this data is not found!")
        } else if (autoService.findAllById(workflow.autoId ?: return).isEmpty()) {
            errors.rejectValue("autoId", "", "Auto with this data is not found!")
        }
    }

}
