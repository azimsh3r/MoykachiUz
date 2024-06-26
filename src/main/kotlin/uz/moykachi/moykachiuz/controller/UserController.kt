package uz.moykachi.moykachiuz.controller

import uz.moykachi.moykachiuz.dto.UserDTO
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.service.UserService
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController @Autowired constructor(val userService: UserService, val modelMapper: ModelMapper) {

    @GetMapping
    private fun findAll() = userService.findAll().map { convertModelToDTO(it) }

    //PROTOTYPE FOR ADMIN PANEL
    @GetMapping("/admin")
    private fun admin() : Map<String,String>{
        userService.admin()
        return mapOf("message" to "hey Admin", )
    }

    private fun convertModelToDTO(user: User) : UserDTO {
        return modelMapper.map(user, UserDTO::class.java)
    }
}
