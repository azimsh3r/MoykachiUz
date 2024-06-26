package uz.moykachi.moykachiuz.repository

import uz.moykachi.moykachiuz.models.AutoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AutoModelRepository : JpaRepository<AutoModel, Int> {
    fun findAllByModel(model : String) : List<AutoModel>
}