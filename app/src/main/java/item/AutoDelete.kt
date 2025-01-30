package item

data class AutoDelete(
    val id: Int,
    val name: String,
    val timer: Int,
    val isRunning: Boolean
)
