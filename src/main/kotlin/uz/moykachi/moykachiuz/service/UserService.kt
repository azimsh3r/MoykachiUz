package uz.moykachi.moykachiuz.service

import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.repository.UserRepository
import uz.moykachi.moykachiuz.security.AuthDetails
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService (@Autowired val userRepository: UserRepository) {

    fun save(user: User) {
        userRepository.save(user)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    fun admin() {}

    fun findAll(): List<User> = userRepository.findAll()

    fun findAllByToken(token: String) : UserDetails = AuthDetails(userRepository.findUserByToken(token))

    fun findAllByPhoneNumber(phoneNumber: String) = userRepository.findAllByPhoneNumber(phoneNumber)
}
