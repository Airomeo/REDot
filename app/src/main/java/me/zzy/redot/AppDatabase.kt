package me.zzy.redot

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.zzy.redot.room.dao.BoardDao
import me.zzy.redot.room.entity.Board


/**
 * @author ZZY
 * 12/21/20.
 */
@Database(entities = [Board::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun boardDao(): BoardDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance: AppDatabase
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "board_attribute")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
                 return instance
//                instance
            }
        }
    }
}