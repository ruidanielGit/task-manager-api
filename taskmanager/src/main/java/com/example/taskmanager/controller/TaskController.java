package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping
    public Page<TaskDTO> findAll(Pageable pageable) {
        return this.taskServiceImpl.findAll(pageable);
    }

    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable @Min(1) Long id) {
        return this.taskServiceImpl.findById(id);
    }

    @GetMapping("/incomplete")
    public Page<TaskDTO> findAllIncomplete(Pageable pageable) {
        return this.taskServiceImpl.getIncomplete(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskDTO taskDto) {
        log.info("Received request to create a new task: {}", taskDto.title());
        return this.taskServiceImpl.create(taskDto);
    }

    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable("id") @Min(1) Long id, @Valid @RequestBody TaskDTO taskDto) {
        log.info("Updating task with id {}: {}", id, taskDto.title());
        return this.taskServiceImpl.update(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @Min(1) Long id) {
        log.info("Deleting task with id {}", id);
        this.taskServiceImpl.delete(id);
    }
}

