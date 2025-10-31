package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class TaskController {

    private static final Task TASK1 = new Task(1, "First task", "Gym", true);
    private static final Task TASK2 = new Task(2, "Second task", "Study Spring", false);
    private static final Task TASK3 = new Task(3, "Third task", "Work on website Agency", false);

    List<Task> TASKS = new ArrayList<>(List.of(TASK1, TASK2, TASK3));

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return TASKS;
    }

    @PostMapping("/tasks")
    public Task add(@RequestBody Task task) {
        TASKS.add(task);
        return task;
    }

    @PutMapping("/tasks/{id}")
    public Task update(@PathVariable("id") int id, @RequestBody Task task) {
        for (int i = 0; i < TASKS.size(); i++) {
            if (TASKS.get(i).getId() == id) {
                TASKS.set(i, task);
                return task;
            }
        }
        return null;
    }

    @DeleteMapping("/tasks/{id}")
    public void delete(@PathVariable("id") int id) {
        TASKS.stream().filter(task -> task.getId() == id)
                .findAny()
                .ifPresent(task -> TASKS.remove(task));
    }
}
