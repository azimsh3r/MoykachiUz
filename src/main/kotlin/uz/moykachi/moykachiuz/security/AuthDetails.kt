package uz.moykachi.moykachiuz.security

import uz.moykachi.moykachiuz.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails (private val user : User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority(user.role))

    override fun getPassword(): String = String()

    override fun getUsername(): String = user.phoneNumber
}
