package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entities.Task;
import com.example.taskmanager.exception.custom.DuplicateResourceException;
import com.example.taskmanager.exception.custom.ResourceNotFoundException;
import com.example.taskmanager.mappers.TaskMapper;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private static final String TASK_NOT_FOUND = "Task with id %d not found";
    private static final String TASK_ALREADY_EXISTS_WITH_TITLE = "Task with title %s already exists";

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public TaskDTO create(TaskDTO taskDto) {
        if (taskDto.title() != null && this.taskRepository.existsByTitleIgnoreCase(taskDto.title())) {
            throw new DuplicateResourceException((String.format(TASK_ALREADY_EXISTS_WITH_TITLE, taskDto.title())));
        }

        Task task = taskMapper.taskDtoToEntity(taskDto);
        Task saved = this.taskRepository.save(task);
        log.info("Created task with id {}", saved.getId());

        return taskMapper.entityToTaskDto(saved);
    }

    @Override
    public TaskDTO update(Long id, TaskDTO taskDto) {
        Task existing = validateAndRetrieveById(id);
        this.taskMapper.updateTaskFromDto(taskDto, existing);

        Task saved = this.taskRepository.saveAndFlush(existing);
        return taskMapper.entityToTaskDto(saved);
    }

    @Override
    public void delete(Long id) {
        validateAndRetrieveById(id);
        this.taskRepository.deleteById(id);
    }

    @Override
    public Page<TaskDTO> getIncomplete(Pageable pageable) {
        return this.taskRepository.findAllIncompleteTasks(pageable).map(taskMapper::entityToTaskDto);
    }

    @Override
    public Page<TaskDTO> findAll(Pageable pageable) {
        return this.taskRepository.findAll(pageable).map(taskMapper::entityToTaskDto);
    }

    @Override
    public TaskDTO findById(Long id) {
        log.debug("Searching for task with id {}", id);
        return taskMapper.entityToTaskDto(validateAndRetrieveById(id));
    }

    private Task validateAndRetrieveById(Long id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(TASK_NOT_FOUND, id)));
    }
}

