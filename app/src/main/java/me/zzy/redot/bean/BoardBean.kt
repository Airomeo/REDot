package me.zzy.redot.bean

/**
 * @author ZZY
 * 1/3/21.
 */
data class BoardBean(
        val speed: Int = 0,
        val clickable: Boolean = false,
        val text: String = "",
        val textSize: Float = 20f,
        val textColor: Int = 0xFFFFFF,
        val textBackgroundColor: Int = 0x000000,
        val isScroll: Boolean = true,
        val direction: ScrollDirection = ScrollDirection.LEFT
)

enum class ScrollDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN
}
