package uz.moykachi.moykachiuz.repository

import uz.moykachi.moykachiuz.models.AutoInstance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AutoInstanceRepository : JpaRepository<AutoInstance, Int> {
    fun findAllByNumber (number: String) : List<AutoInstance>
}