package uz.moykachi.moykachiuz.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "auto_brand" )//, schema = "moykachi")
class AutoBrand {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    var id: Int = 0

    @Column(name = "brand")
    lateinit var brand: String

    @Column(name = "logo")
    var logo: String? = null

    @OneToMany(mappedBy = "autoBrand")
    @Transient
    @JsonIgnore
    var autoModel: List<AutoModel>? = emptyList()
}
