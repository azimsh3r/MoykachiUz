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
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.function.Consumer

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService, val jwtUtil: JWTUtil) {
    @PostMapping("/login")
    fun login(@RequestBody response : String) {
        println(response)
        authService.processMessage(response)
    }

    @PostMapping("/processLogin")
    fun performRegistration(
        @Valid @RequestBody authPrincipal: AuthPrincipalDTO,
        bindingResult: BindingResult
    ) : ResponseEntity<out Any> {
        if(bindingResult.hasErrors()) {
            val stringBuilder = StringBuilder()
            val fieldErrorList = bindingResult.fieldErrors
            fieldErrorList.forEach(Consumer { fieldError: FieldError ->
                stringBuilder.append(
                    fieldError.defaultMessage
                ).append(" ")
            })
            return ResponseEntity(ExceptionResponse(stringBuilder.toString()), HttpStatus.BAD_REQUEST)
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