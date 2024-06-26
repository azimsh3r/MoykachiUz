package uz.moykachi.moykachiuz.dto

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserDTO {
    var firstName: String? = null

    var lastName: String? = null

    var phoneNumber: String? = null

    var createdAt: LocalDateTime = LocalDateTime.now()
}