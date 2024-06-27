package uz.moykachi.moykachiuz.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.models.WashRegistry
import uz.moykachi.moykachiuz.repository.WashRegistryRepository
import java.time.LocalDateTime

@Service
@Transactional
class WashRegistryService @Autowired constructor(private val washRegistryRepository: WashRegistryRepository) {

    private fun findLastWorkByUser(user: User) : WashRegistry? = washRegistryRepository.findLastWorkflowByUser(user)

    fun isBusy(user: User) : Boolean = findLastWorkByUser(user)?.finishedAt != null

    fun finishWash(userId : Int) {
        val workFlow = findLastWorkByUser(User().apply { id = userId })
        workFlow?.finishedAt = LocalDateTime.now()
    }

    fun save(washRegistry: WashRegistry) {
        washRegistryRepository.save(washRegistry)
    }
}
