package uz.moykachi.moykachiuz.exception

import java.time.LocalDateTime

class ExceptionResponse(var message: String) {
    var timeStamp: LocalDateTime = LocalDateTime.now()
}
