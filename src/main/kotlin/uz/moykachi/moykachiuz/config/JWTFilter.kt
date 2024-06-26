package uz.moykachi.moykachiuz.config

import com.auth0.jwt.exceptions.JWTVerificationException
import uz.moykachi.moykachiuz.service.UserService
import uz.moykachi.moykachiuz.security.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTFilter @Autowired constructor(val jwtUtil: JWTUtil, val userService: UserService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeaders = request.getHeader("Authorization")
        if (authHeaders != null && authHeaders.isNotBlank() && authHeaders.startsWith("Bearer ")) {
            val jwt = authHeaders.substring(7)
            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authentication Failed! Invalid JWT Token!")
            } else {
                try {
                    val token : String = jwtUtil.validateTokenAndRetrieveData(jwt)
                    val userDetails: UserDetails = userService.findAllByToken(token)
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
                    if (SecurityContextHolder.getContext().authentication == null) {
                        SecurityContextHolder.getContext().authentication = authenticationToken
                    }
                } catch (e: JWTVerificationException) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authentication Failed! Invalid JWT Token!")
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}
