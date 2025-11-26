package com.example.taskmanager.repository;

import com.example.taskmanager.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByTitleIgnoreCase(String title);

    @Query("SELECT t from Task t where t.done = false")
    List<Task> findAllIncompleteTasks();
}
