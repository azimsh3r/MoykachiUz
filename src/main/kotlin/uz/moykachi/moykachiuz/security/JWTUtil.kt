package uz.moykachi.moykachiuz.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class JWTUtil {
    @Value("thisissecret")
    private val secret: String? = null

    fun generateToken(token: String?): String {
        return JWT.create()
            .withSubject("ProgrammerUz")
            .withClaim("token", token)
            .withIssuedAt(Date())
            .withIssuer("https://www.moykachi.uz/api")
            .withExpiresAt(Date.from(ZonedDateTime.now().plusHours(5).toInstant()))
            .sign(Algorithm.HMAC256(secret))
    }

    fun validateTokenAndRetrieveData(token: String?): String {
        val jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
            .withIssuer("https://www.moykachi.uz/api")
            .withSubject("ProgrammerUz")
            .build()
        val jwt = jwtVerifier.verify(token)
        return jwt.getClaim("token").asString()
    }
}
