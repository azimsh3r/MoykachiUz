package uz.moykachi.moykachiuz.controller

import uz.moykachi.moykachiuz.dto.AuthPrincipalDTO
import uz.moykachi.moykachiuz.exception.ExceptionResponse
import uz.moykachi.moykachiuz.exception.InvalidCredentialsException
import uz.moykachi.moykachiuz.service.AuthService
import uz.moykachi.moykachiuz.security.JWTUtil
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uz.moykachi.moykachiuz.util.ValidationExceptionHandler
import java.time.LocalDateTime

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService, val jwtUtil: JWTUtil, val validationExceptionHandler: ValidationExceptionHandler) {
    @PostMapping("/login")
    fun login(@RequestBody response : String) {
        authService.processMessage(response)
    }

    @PostMapping("/processLogin")
    fun performRegistration(
        @Valid @RequestBody authPrincipal: AuthPrincipalDTO,
        bindingResult: BindingResult
    ) : ResponseEntity<out Any> {
        if(bindingResult.hasErrors()) {
            return validationExceptionHandler.handleValidationException(bindingResult)
        }

        try {
            authService.verifyOTP(authPrincipal)
        } catch (ex: InvalidCredentialsException) {
            return ResponseEntity(ExceptionResponse(ex.message ?: "Authentication Failed! Unauthorized!"), HttpStatus.BAD_REQUEST)
        }

        val jwt: String = jwtUtil.generateToken(authPrincipal.token)

        return ResponseEntity(
            mapOf(
                Pair("token", jwt),
                Pair("timestamp", LocalDateTime.now())
            ), HttpStatus.OK)
    }
}