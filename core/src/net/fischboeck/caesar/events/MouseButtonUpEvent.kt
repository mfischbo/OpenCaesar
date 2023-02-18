package net.fischboeck.caesar.events

data class MouseButtonUpEvent(
    val screenX: Int,
    val screenY: Int,
    val button: Int,
    val gridX: Int,
    val gridY: Int
)
