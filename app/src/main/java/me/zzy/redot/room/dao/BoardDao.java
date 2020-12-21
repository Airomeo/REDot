package me.zzy.redot.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import me.zzy.redot.room.entity.Board;


/**
 * @author ZZY
 * 12/21/20.
 */
@Dao
public interface BoardDao {
    @Query("SELECT * FROM Board")
    List<Board> getAll();

    @Query("SELECT * FROM Board WHERE boardId IN (:boardIds)")
    List<Board> loadAllByIds(int[] boardIds);

    @Query("SELECT * FROM Board WHERE boardId = :boardId")
    Board findById(int boardId);

    @Query("SELECT * FROM Board WHERE text LIKE :text LIMIT 1")
    Board findByText(String text);

    @Insert
    void insertAll(Board... boards);

    @Update
    public void updateUsers(Board... boards);

    @Delete
    void delete(Board board);
}
