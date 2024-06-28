package uz.moykachi.moykachiuz.repository

import uz.moykachi.moykachiuz.models.AuthPrincipal
import uz.moykachi.moykachiuz.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthenticationRepository : JpaRepository<AuthPrincipal, Int> {

    fun findAllByToken(token : String) : List<AuthPrincipal>

    fun findAllByChatId(chatId: Int) : List<AuthPrincipal>
}