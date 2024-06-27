package uz.moykachi.moykachiuz.util

import uz.moykachi.moykachiuz.dto.AutoDTO
import uz.moykachi.moykachiuz.service.AutoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import uz.moykachi.moykachiuz.service.UserService

@Component
class AutoValidator @Autowired constructor(val autoService: AutoService, val userService: UserService) : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return clazz == AutoDTO::class.java
    }

    override fun validate(target: Any, errors: Errors) {
        val auto : AutoDTO = target as AutoDTO
        if (autoService.getAutoByNumber(auto.number!!).isNotEmpty()) {
            errors.rejectValue("number", "", "Auto with this data already exists!")
        }
    }
}
