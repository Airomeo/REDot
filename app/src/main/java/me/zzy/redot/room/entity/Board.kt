package me.zzy.redot.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author ZZY
 * 12/2/20.
 */
@Entity
data class Board(
        @PrimaryKey(autoGenerate = true)
        var boardId: Int = 0,

        @ColumnInfo(name = "text")
        var text: String? = null,

        @ColumnInfo(name = "text_size")
        var textSize: Float = 0f,

        @ColumnInfo(name = "text_color")
        var textColor: Int = 0,

        @ColumnInfo(name = "background_color")
        var backgroundColor: Int = 0,

        @ColumnInfo(name = "speed")
        var speed: Int = 0,

        @ColumnInfo(name = "is_horizontal")
        var isHorizontal: Boolean = false,

        @ColumnInfo(name = "clickable")
        var clickable: Boolean = false,

        @ColumnInfo(name = "is_scroll_forever")
        var isScroll: Boolean = false,

)