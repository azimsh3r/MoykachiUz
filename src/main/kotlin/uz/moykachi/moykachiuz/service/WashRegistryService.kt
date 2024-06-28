package uz.moykachi.moykachiuz.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.moykachi.moykachiuz.exception.InvalidDataException
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.models.WashRegistry
import uz.moykachi.moykachiuz.repository.WashRegistryRepository
import java.time.LocalDateTime

@Service
@Transactional
class WashRegistryService @Autowired constructor(private val washRegistryRepository: WashRegistryRepository) {

    private fun findLastWorkByUser(userId: Int) : List<WashRegistry> = washRegistryRepository.findLastWorkflowByUser(userId)

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

    fun save(washRegistry: WashRegistry) {
        washRegistryRepository.save(washRegistry)
    }
}
