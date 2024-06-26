package uz.moykachi.moykachiuz.dto

import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull

class AutoDTO {
    @NotNull("AutoBrand is null")
    var brand: String? = null

    @NotNull("AutoModel is null")
    var model: String? = null

    @Size(min = 3, message = "Color is invalid")
    @NotNull("Color is null")
    var color : String? = null

    @NotNull("Number is null")
    @Size(min=6, max = 6, message = "Car Plate Number is invalid")
    var number: String? = null
}
