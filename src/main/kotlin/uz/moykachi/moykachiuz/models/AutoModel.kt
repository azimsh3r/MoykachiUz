package uz.moykachi.moykachiuz.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import uz.moykachi.moykachiuz.models.AutoBrand
import uz.moykachi.moykachiuz.models.AutoInstance
import kotlin.jvm.Transient

@Entity
@Table(name = "auto_model")//, schema = "moykachi")
class AutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    var id: Int = 0

    @Column(name = "model")
    lateinit var model: String

    @OneToMany(mappedBy = "autoModel")
    @JsonIgnore
    @Transient
    var autoInstanceList: List<AutoInstance>? = null

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    lateinit var autoBrand: AutoBrand
}
