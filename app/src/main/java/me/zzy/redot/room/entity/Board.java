package me.zzy.redot.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ZZY
 * 12/2/20.
 */
@Entity
public class Board {
    @PrimaryKey(autoGenerate = true)
    public int boardId;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "text_size")
    public float textSize;

    @ColumnInfo(name = "text_color")
    public int text_color;

    @ColumnInfo(name = "background_color")
    public int background_color;

    @ColumnInfo(name = "speed")
    public int speed;

    @ColumnInfo(name = "is_horizontal")
    public boolean isHorizontal;

    @ColumnInfo(name = "clickable")
    public boolean clickable;

    @ColumnInfo(name = "is_scroll_forever")
    public boolean is_scroll_forever;

    @ColumnInfo(name = "times")
    public int times;

}