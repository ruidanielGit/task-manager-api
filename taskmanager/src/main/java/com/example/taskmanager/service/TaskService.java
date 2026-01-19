package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskDTO create(TaskDTO taskDto);

    TaskDTO update(Long id, TaskDTO taskDto);

    void delete(Long id);

    Page<TaskDTO> getIncomplete(Pageable pageable);

    Page<TaskDTO> findAll(Pageable pageable);

    TaskDTO findById(Long id);
}
