package com.example.afe.task.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = "user_task_join", indices = @Index("task_id"), primaryKeys = {"user_id","task_id"}, foreignKeys = {
        @ForeignKey( entity = User.class,
                    parentColumns = "user_id",
                    childColumns = "user_id"),
        @ForeignKey(entity = Task.class,
                    parentColumns = "task_id",
                    childColumns = "task_id")

})
public class UserTaskJoin {
    @ColumnInfo(name="user_id")
    private int userId;
    @ColumnInfo(name="task_id")
    private int taskId;

    public UserTaskJoin(int userId, int taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTaskId() {
        return taskId;
    }
}
