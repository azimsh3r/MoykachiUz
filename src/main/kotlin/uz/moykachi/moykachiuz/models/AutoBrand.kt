package uz.moykachi.moykachiuz.models

import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "auto_brand" )//, schema = "moykachi")
class AutoBrand {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(name = "brand")
    lateinit var brand: String

    @Column(name = "logo")
    lateinit var logo: String

    @OneToMany(mappedBy = "autoBrand")
    @Transient
    var autoModel: List<AutoModel>? = emptyList()
}
