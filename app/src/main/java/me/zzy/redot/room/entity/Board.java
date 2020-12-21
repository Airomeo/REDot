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
    public String text_color;

    @ColumnInfo(name = "background_color")
    public String background_color;

    @ColumnInfo(name = "speed")
    public String speed;

    @ColumnInfo(name = "is_horizontal")
    public String isHorizontal;

    @ColumnInfo(name = "clickable")
    public String clickable;

    @ColumnInfo(name = "is_scroll_forever")
    public String is_scroll_forever;

    @ColumnInfo(name = "times")
    public String times;

}