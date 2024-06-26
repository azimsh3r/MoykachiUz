package uz.moykachi.moykachiuz.models

import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
@Table(name = "auto_user", schema = "moykachi")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "role")
    lateinit var role: String

    @Column(name = "phone_number")
    lateinit var phoneNumber: String

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Transient
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var authPrincipal: AuthPrincipal? = null
}
