package uz.moykachi.moykachiuz.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="authentication_principal")//, schema = "moykachi")
class AuthPrincipal {
    @Column(name = "otp")
    var otp: Int? = null

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OneToOne
    lateinit var user: User

    @Column(name="token")
    var token : String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    var id: Int = 0

    @Column(name = "expires_at")
    var expiresAt: LocalDateTime = LocalDateTime.now().plusMinutes(1)

    @Column(name = "chat_id")
    var chatId: Int = 0
}