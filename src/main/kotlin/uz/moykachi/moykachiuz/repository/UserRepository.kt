package uz.moykachi.moykachiuz.repository

import uz.moykachi.moykachiuz.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findAllByPhoneNumber(phoneNumber: String) : List<User>

    @Query("Select u FROM User u JOIN AuthPrincipal ap ON u.id = ap.user.id WHERE ap.token = :token")
    fun findUserByToken (@Param("token") token: String) : User

    fun findAllById(id: Int) : List<User>
}