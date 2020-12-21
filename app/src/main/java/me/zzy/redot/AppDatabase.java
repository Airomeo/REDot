package me.zzy.redot;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.zzy.redot.room.dao.BoardDao;
import me.zzy.redot.room.entity.Board;

/**
 * @author ZZY
 * 12/21/20.
 */
@Database(entities = {Board.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BoardDao boardDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "board_attribute")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
