package uz.moykachi.moykachiuz.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "auto_instance" )//, schema = "moykachi")
class AutoInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    var id: Int = 0

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    lateinit var autoModel: AutoModel

    @Column(name = "color")
    lateinit var color: String

    @Column(name = "number")
    lateinit var number: String

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    lateinit var user: User

    @OneToMany(mappedBy = "auto")
    lateinit var workflowList : List<Workflow>
}
