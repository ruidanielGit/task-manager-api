package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entities.Task;
import com.example.taskmanager.exception.custom.ResourceNotFoundException;
import com.example.taskmanager.mappers.TaskMapper;
import com.example.taskmanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskService {

    private static final String TASK_NOT_FOUND = "Task with id %d not found";
    private static final String TASK_ALREADY_EXISTS = "Task with id %d already exists";
    private static final String TASK_ALREADY_EXISTS_WITH_TITLE = "Task with title %s already exists";

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    public List<TaskDTO> getTasks() {
        return this.taskRepository
                .findAll()
                .stream()
                .map(taskMapper::entityToTaskDto)
                .toList();
    }

    public TaskDTO findTaskById(Long id) {
        log.debug("Searching for task with id {}", id);
        return taskMapper.entityToTaskDto(validateAndRetrieveById(id));
    }

    public TaskDTO create(TaskDTO taskDto) {
        if (taskDto.getTitle() != null && this.taskRepository.existsByTitleIgnoreCase(taskDto.getTitle())) {
            throw new RuntimeException((String.format(TASK_ALREADY_EXISTS_WITH_TITLE, taskDto.getTitle())));
        }

        Task task = taskMapper.taskDtoToEntity(taskDto);

        try {
            Task saved = this.taskRepository.saveAndFlush(task);
            log.info("Created task with id {}", saved.getId());
            return taskMapper.entityToTaskDto(saved);
        } catch (Exception e) {
            throw new RuntimeException(String.format(TASK_ALREADY_EXISTS, task.getId()));
        }
    }

    public TaskDTO update(Long id, TaskDTO taskDto) {
        Task existing = validateAndRetrieveById(id);
        this.taskMapper.updateTaskFromDto(taskDto, existing);

        Task saved = this.taskRepository.saveAndFlush(existing);
        return taskMapper.entityToTaskDto(saved);
    }

    public void delete(Long id) {
        validateAndRetrieveById(id);
        this.taskRepository.deleteById(id);
    }

    private Task validateAndRetrieveById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task with id {} not found", id);
                    return new ResourceNotFoundException(String.format(TASK_NOT_FOUND, id));
                });
    }
}

