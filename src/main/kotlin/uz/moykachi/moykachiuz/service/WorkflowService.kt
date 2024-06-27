package uz.moykachi.moykachiuz.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uz.moykachi.moykachiuz.models.Workflow
import uz.moykachi.moykachiuz.repository.WorkflowRepository

@Service
class WorkflowService @Autowired constructor(private val workflowRepository: WorkflowRepository) {

    fun isBusy() : Boolean {
        TODO("Write a function to identify if employee is busy")

    }

    fun save(workflow: Workflow) {
        workflowRepository.save(workflow)
    }
}