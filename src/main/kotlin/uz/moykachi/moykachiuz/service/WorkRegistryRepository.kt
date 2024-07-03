package uz.moykachi.moykachiuz.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.moykachi.moykachiuz.exception.InvalidDataException
import uz.moykachi.moykachiuz.models.WorkRegistry
import uz.moykachi.moykachiuz.repository.WorkRegistryRepository
import java.time.LocalDateTime

@Service
@Transactional
class WorkRegistryRepository @Autowired constructor(private val workRegistryRepository: WorkRegistryRepository) {

    private fun findLastWorkByUser(userId: Int) : List<WorkRegistry> = workRegistryRepository.findLastWorkflowByUser(userId)

    fun isBusy(userId: Int) : Boolean {
        val last = findLastWorkByUser(userId)
        if (last.isNotEmpty()) {
            return last.first().finishedAt == null
        }
        return false
    }

    fun finishWash(userId : Int) {
        val employeeList = findLastWorkByUser(userId)
        if (employeeList.isEmpty()) {
            throw InvalidDataException("Employee with this data is not found!")
        } else if(employeeList.first().finishedAt != null) {
            throw RuntimeException("There are no current washes for this User")
        } else {
            employeeList.first().finishedAt = LocalDateTime.now()
            save(employeeList.first())
        }
    }

    fun save(workRegistry: WorkRegistry) {
        workRegistryRepository.save(workRegistry)
    }
}
