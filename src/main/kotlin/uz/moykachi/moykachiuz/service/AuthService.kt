package uz.moykachi.moykachiuz.service

import com.google.gson.Gson
import com.google.gson.JsonObject
import uz.moykachi.moykachiuz.dto.AuthPrincipalDTO
import uz.moykachi.moykachiuz.exception.InvalidCredentialsException
import uz.moykachi.moykachiuz.models.AuthPrincipal
import uz.moykachi.moykachiuz.models.User
import uz.moykachi.moykachiuz.repository.AuthenticationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import java.time.LocalDateTime
import java.util.Random

@Service
@Transactional
class AuthService @Autowired constructor(val userService: UserService, val authenticationRepository: AuthenticationRepository) {
    companion object {
        const val BOT_TOKEN = "7457073052:AAE52NfPv2qIa59C92c1areHyHZVzoW5274"
    }

    fun generateOTP () = Random().nextInt(100000, 999999)

    fun verifyOTP (authPrincipal: AuthPrincipalDTO) {
        val userList : List<AuthPrincipal> = authenticationRepository.findAllByToken(authPrincipal.token)
        if (userList.isEmpty() || userList[0].otp != authPrincipal.otp) {
            throw InvalidCredentialsException()
        } else if (userList.first().expiresAt.isBefore(LocalDateTime.now())) {
            throw InvalidCredentialsException("The one-time password expired!");
        }
    }

    fun processMessage (response: String) {
        val jsonObject = Gson().fromJson(response, JsonObject::class.java)

        val message = jsonObject.getAsJsonObject("message") ?: jsonObject.getAsJsonObject("my_chat_member")

        message?.let { it ->
            val text = it.get("text")?.asString ?: "Invalid"
            val chat = it.getAsJsonObject("chat")

            chat?.let {
                val name: String = it.get("first_name")?.asString ?: "Xurmatli Foydalanuvchi"
                val chatId: Int = it.get("id")?.asInt ?: 0

                if (text.contains("/start") && text.length > 7) {
                    handleStartCommand(text, name, chatId)
                } else if (response.contains("\"contact\":{\"phone_number\"") && verifyPhoneNumber(message, chatId)) {
                    handleContactMessage(message, chatId)
                }
            }
        }
    }

    private fun handleStartCommand (text: String, name: String, chatId: Int) {
        sendMessage("Salom $name \uD83D\uDC4B\nBotga xush kelibsiz\n⬇\uFE0F Kontaktingizni yuboring (tugmani bosib)", chatId, true)

        val token = text.substringAfter("/start ")

        val regAuthPrincipalList = authenticationRepository.findAllByChatId(chatId)

        val authPrincipal = if (regAuthPrincipalList.isNotEmpty()) {
            val existingAuthPrincipal = regAuthPrincipalList.first()
            existingAuthPrincipal.token = token
            existingAuthPrincipal
        } else {
            AuthPrincipal().apply {
                this.chatId = chatId
                this.token = token
            }
        }

        authenticationRepository.save(authPrincipal)
    }

    private fun handleContactMessage (message: JsonObject, chatId: Int) {
        val otp = generateOTP()

        val user = extractUserDataFromMessage(message)

        val regUserList = userService.findAllByPhoneNumber(user.phoneNumber)

        if (regUserList.isNotEmpty()) {
            user.id = regUserList.first().id
        }
        userService.save(user)

        val regAuthPrincipalList = authenticationRepository.findAllByChatId(chatId)
        val authPrincipal = AuthPrincipal().apply {
            this.otp = otp
            this.user = user
        }

        if(regAuthPrincipalList.isNotEmpty()) {
            authPrincipal.id = regAuthPrincipalList.first().id
            authPrincipal.chatId = regAuthPrincipalList.first().chatId
            authPrincipal.token = regAuthPrincipalList.first().token
        }
        authenticationRepository.save(authPrincipal)

        sendMessage("Sizning parolingiz: $otp", chatId)
    }

    private fun extractUserDataFromMessage (message : JsonObject) : User {
        val user = User()
        user.firstName = message.get("chat")?.asJsonObject?.get("first_name")?.asString

        user.lastName = message.get("chat")?.asJsonObject?.get("last_name")?.asString

        user.phoneNumber = message.get("contact").asJsonObject.get("phone_number").asString
        if (!user.phoneNumber.startsWith("+")) {
            user.phoneNumber = "+${user.phoneNumber}"
        }

        user.role = "ROLE_USER"
        return user
    }

    private fun sendMessage (text : String, chatId : Int, requestContact : Boolean = false) {
        val keyboard = mapOf(
            "keyboard" to listOf(
                listOf(mapOf("text" to "Get Contact", "request_contact" to requestContact)),
            ),
            "resize_keyboard" to true,
            "one_time_keyboard" to true
        )

        RestClient
            .builder()
            .baseUrl("https://api.telegram.org/bot$BOT_TOKEN")
            .build()
            .post()
            .uri("/sendMessage")
            .accept(MediaType.APPLICATION_JSON)
            .body(
                mapOf (
                    "chat_id" to chatId,
                    "text" to text,
                    "reply_markup" to keyboard
                )
            )
            .retrieve()
            .toBodilessEntity()
    }

    private fun verifyPhoneNumber (message : JsonObject, chatId: Int) : Boolean {
        val userId = message.get("contact")?.asJsonObject?.get("user_id")?.asInt
        userId?.let {
            return userId == chatId
        }
        return false
    }
}
