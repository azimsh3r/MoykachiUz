package uz.moykachi.moykachiuz.service

import uz.moykachi.moykachiuz.dto.AutoDTO
import uz.moykachi.moykachiuz.models.AutoBrand
import uz.moykachi.moykachiuz.models.AutoModel
import uz.moykachi.moykachiuz.models.AutoInstance
import uz.moykachi.moykachiuz.repository.AutoBrandRepository
import uz.moykachi.moykachiuz.repository.AutoModelRepository
import uz.moykachi.moykachiuz.repository.AutoInstanceRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AutoService @Autowired constructor (private val autoBrandRepository: AutoBrandRepository, private val autoModelRepository: AutoModelRepository, private val autoInstanceRepository: AutoInstanceRepository, val modelMapper : ModelMapper) {

    fun findAll() : List<AutoDTO> = autoInstanceRepository.findAll().map {convertModelToDTO(it)}

    fun getAutoByNumber (plateNumber : String) : List<AutoInstance> = autoInstanceRepository.findAllByNumber(plateNumber)

    fun save (autoDTO: AutoDTO) {
        val autoBrand  = modelMapper.map(autoDTO, AutoBrand::class.java)
        val autoModel = modelMapper.map(autoDTO, AutoModel::class.java)
        val autoInstance = modelMapper.map(autoDTO, AutoInstance::class.java)

        //Saves autoBrand data
        val foundBrand = autoBrandRepository.findAllByBrand(autoBrand.brand)
        if (foundBrand.isNotEmpty()) {
            autoBrand.id = foundBrand.first().id
        }
        autoBrandRepository.save(autoBrand)
        autoModel.autoBrand = autoBrand

        //Saves autoModel data
        val foundModel = autoModelRepository.findAllByModel(autoModel.model)
        if (foundModel.isNotEmpty()) {
            autoModel.id = foundModel.first().id
        }
        autoModelRepository.save(autoModel)
        autoInstance.autoModel = autoModel

        //Saves individual car instance
        autoInstanceRepository.save(autoInstance)
    }

    fun findAllById(id : Int) = autoInstanceRepository.findAllById(id)

    private fun convertModelToDTO(autoInstance: AutoInstance) : AutoDTO {
        val autoDTO = AutoDTO()
        autoDTO.number = autoInstance.number
        autoDTO.model = autoInstance.autoModel.model
        autoDTO.brand = autoInstance.autoModel.autoBrand.brand
        autoDTO.color = autoInstance.color
        return autoDTO
    }
}
