package uz.moykachi.moykachiuz.exception

import java.time.LocalDateTime

class SuccessResponse (val message : String) {
    val date : LocalDateTime = LocalDateTime.now()
}