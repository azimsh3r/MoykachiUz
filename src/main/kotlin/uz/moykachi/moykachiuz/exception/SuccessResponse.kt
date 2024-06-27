package uz.moykachi.moykachiuz.exception

import java.time.LocalDateTime

class SuccessResponse (val message : String = "Successful!") {
    val date : LocalDateTime = LocalDateTime.now()
}