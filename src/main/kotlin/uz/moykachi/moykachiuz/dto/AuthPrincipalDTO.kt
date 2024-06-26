package uz.moykachi.moykachiuz.dto

import org.jetbrains.annotations.NotNull
import kotlin.properties.Delegates

class AuthPrincipalDTO {
    @NotNull("One-Time password cannot be null")
    var otp : Int = 0

    @NotNull("AuthToken cannot be null!")
    lateinit var token : String
}
