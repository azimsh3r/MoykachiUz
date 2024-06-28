package uz.moykachi.moykachiuz.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "auto_wash_registry")//, schema = "moykachi")
class WorkRegistry {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    lateinit var user: User

    @ManyToOne
    @JoinColumn(name = "auto_id", referencedColumnName = "id")
    lateinit var auto: AutoInstance

    @Column(name="started_at")
    val startedAt : LocalDateTime = LocalDateTime.now()

    @Column(name="finished_at")
    var finishedAt: LocalDateTime? = null
}
