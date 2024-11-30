package com.example.spring_project_work.controller;

import com.example.spring_project_work.domain.EstimationAccuracy;
import com.example.spring_project_work.domain.Task;
import com.example.spring_project_work.dto.TaskDTO;
import com.example.spring_project_work.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.listAllTasks();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/state/{state}")
    public List<Task> getAllTasksByState(@PathVariable String state) {
        return taskService.getAllTasksByState(state);
    }

    @GetMapping("owner/{ownerId}")
    public ResponseEntity<List<Task>> getAllTasksByOwner(@PathVariable Long ownerId) {
        return taskService.getAllTasksByOwnerId(ownerId);
    }

    @GetMapping("/{taskId}/estimation-accuracy")
    public ResponseEntity<EstimationAccuracy> getEstimationAccuracyOfTaskWithId(@PathVariable Long taskId) {
        return taskService.computeEstimationAccuracy(taskId);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PatchMapping("/{taskId}/remaining-hours")
    public ResponseEntity<Void> updateTask(@PathVariable Long taskId, @RequestParam int remainingHours) {
        return taskService.updateTaskRemainingHours(taskId, remainingHours);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTaskById(taskId);
    }



}
