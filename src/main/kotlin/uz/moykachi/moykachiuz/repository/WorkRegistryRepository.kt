package uz.moykachi.moykachiuz.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import uz.moykachi.moykachiuz.models.WorkRegistry

@Repository
interface WorkRegistryRepository : JpaRepository<WorkRegistry, Int> {

    @Query("FROM WorkRegistry WHERE user.id = :userId ORDER BY id DESC")
    fun findLastWorkflowByUser(@Param("userId") userId : Int) : List<WorkRegistry>
}