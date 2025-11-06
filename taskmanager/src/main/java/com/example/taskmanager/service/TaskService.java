package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entities.Task;
import com.example.taskmanager.exception.custom.ResourceNotFoundException;
import com.example.taskmanager.mappers.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    private static final Task TASK_DTO_1 = new Task(1, "First task", "Gym", true);
    private static final Task TASK_DTO_2 = new Task(2, "Second task", "Study Spring", false);
    private static final Task TASK_DTO_3 = new Task(3, "Third task", "Work on website Agency", false);
    private static final String TASK_NOT_FOUND = "Task with id %d not found";
    private static final String TASK_ALREADY_EXISTS = "Task with id %d already exists";

    private final TaskMapper taskMapper;
    List<Task> taskList = new ArrayList<>(List.of(TASK_DTO_1, TASK_DTO_2, TASK_DTO_3));

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public List<TaskDTO> getTasks() {
        return taskList.stream()
                .map(taskMapper::entityToTaskDto)
                .toList();
    }

    public TaskDTO findTaskById(int id) {
        log.debug("Searching for task with id {}", id);
        return taskList.stream()
                .filter(taskDto -> taskDto.getId() == id)
                .findFirst()
                .map(taskMapper::entityToTaskDto)
                .orElseThrow(() -> {
                    log.warn("Task with id {} not found", id);
                    return new ResourceNotFoundException(String.format(TASK_NOT_FOUND, id));
                });
    }

    public TaskDTO create(TaskDTO taskDto) {
        Task task = taskMapper.taskDtoToEntity(taskDto);
        for (Task value : taskList) {
            if (value.getId() == task.getId()) {
                throw new RuntimeException(String.format(TASK_ALREADY_EXISTS, task.getId()));
            }
        }
        taskList.add(task);
        log.info("Created task with id {}", task.getId());
        return taskMapper.entityToTaskDto(task);
    }

    public TaskDTO update(int id, TaskDTO taskDto) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == id) {
                Task updatedTask = taskMapper.taskDtoToEntity(taskDto);
                taskList.set(i, updatedTask);
                return taskMapper.entityToTaskDto(updatedTask);
            }
        }
        throw new ResourceNotFoundException(String.format(TASK_NOT_FOUND, id));
    }

    public void delete(int id) {
        boolean removed = taskList.removeIf(task -> task.getId() == id);
        if (!removed) throw new ResourceNotFoundException(String.format(TASK_NOT_FOUND, id));
    }
}

