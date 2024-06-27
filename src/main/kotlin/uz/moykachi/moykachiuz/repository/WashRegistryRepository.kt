package uz.moykachi.moykachiuz.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.models.WashRegistry

@Repository
interface WashRegistryRepository : JpaRepository<WashRegistry, Int> {

    @Query("FROM WashRegistry WHERE user = :user ORDER BY id DESC")
    fun findLastWorkflowByUser(@Param("user") user : User) : WashRegistry?
}