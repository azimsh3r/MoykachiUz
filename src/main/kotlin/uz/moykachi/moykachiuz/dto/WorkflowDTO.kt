package uz.moykachi.moykachiuz.dto

import org.jetbrains.annotations.NotNull

class WorkflowDTO {
    @NotNull("userId cannot be null!")
    var userId : Int? = null

    @NotNull("autoId cannot be null!")
    var autoId: Int? = null
}