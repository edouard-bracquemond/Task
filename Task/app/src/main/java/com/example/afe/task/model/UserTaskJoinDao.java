package com.example.afe.task.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserTaskJoinDao {
    @Insert
    void insertUserTaskJoin(UserTaskJoin userTaskJoin);

    @Delete
    void deleteUserTaskJoin(UserTaskJoin userTaskJoin);

    @Update
    void updateUserTaskJoin(UserTaskJoin userTaskJoin);


    @Query("SELECT * FROM user INNER JOIN user_task_join ON user.user_id=user_task_join.user_id WHERE user_task_join.task_id=:taskId")
    List<User> getUsersForTask(int taskId);

    @Query("SELECT * FROM task INNER JOIN user_task_join ON task.task_id=user_task_join.task_id WHERE user_task_join.user_id=:userId")
    List<Task> getTaskForUser(int userId);
}
