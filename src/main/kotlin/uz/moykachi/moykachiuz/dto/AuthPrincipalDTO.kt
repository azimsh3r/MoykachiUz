package uz.moykachi.moykachiuz.dto

import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

class AuthPrincipalDTO {
    @NotNull("One-Time password cannot be null")
    @Size(min=6, max = 6, message = "One-Time password should consist of 6 digits")
    var otp : Int = 0

    @NotNull("AuthToken cannot be null!")
    lateinit var token : String
}
