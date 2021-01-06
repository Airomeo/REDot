package me.zzy.redot.room.dao

import androidx.room.*
import me.zzy.redot.room.entity.Board

/**
 * @author ZZY
 * 12/21/20.
 */
@Dao
interface BoardDao {
    @Query("SELECT * FROM board")
    fun getAll(): Array<Board>

    @Query("SELECT * FROM Board WHERE boardId IN (:boardIds)")
    fun loadAllByIds(boardIds: IntArray?): Array<Board?>?

    @Query("SELECT * FROM Board WHERE boardId = :boardId")
    fun findById(boardId: Int): Board?

    @Query("SELECT * FROM Board WHERE text LIKE :text LIMIT 1")
    fun findByText(text: String?): Board?

    @Insert
    fun insertAll(vararg boards: Board?)

    @Update
    fun updateUsers(vararg boards: Board?)

    @Delete
    fun delete(board: Board?)
}