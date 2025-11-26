package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskDTO> findAll(Pageable pageable) {
        return this.taskService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable @Min(1) Long id) {
        return this.taskService.findById(id);
    }

    @GetMapping("/incomplete")
    public List<TaskDTO> findAllIncomplete() {
        return this.taskService.getIncomplete();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskDTO taskDto) {
        log.info("Received request to create a new task: {}", taskDto.getTitle());
        return this.taskService.create(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody TaskDTO taskDto) {
        log.info("Updating task with id {}: {}", id, taskDto.getTitle());
        return this.taskService.update(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(1) Long id) {
        log.info("Deleting task with id {}", id);
        this.taskService.delete(id);
    }
}

