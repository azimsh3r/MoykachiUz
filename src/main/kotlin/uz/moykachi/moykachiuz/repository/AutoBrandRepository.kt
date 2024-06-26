package uz.moykachi.moykachiuz.repository

import uz.moykachi.moykachiuz.models.AutoBrand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AutoBrandRepository : JpaRepository<AutoBrand, Int> {
    fun findAllByBrand(brand : String) : List<AutoBrand>
}