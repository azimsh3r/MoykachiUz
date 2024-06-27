package uz.moykachi.moykachiuz.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.models.Workflow

@Repository
interface WorkflowRepository : JpaRepository<Workflow, Int> {
}