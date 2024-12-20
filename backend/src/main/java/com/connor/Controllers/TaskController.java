package com.connor.Controllers;

import com.connor.Models.Task;
import com.connor.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
public class TaskController {

    private final TaskService _taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        _taskService = taskService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("api/tasks")
    public ResponseEntity<List<Task>> getTasks() throws ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(_taskService.getTasks().get());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("api/tasks/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable Long id) throws ExecutionException, InterruptedException {
        Task task = _taskService.getTaskById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(Objects.requireNonNullElse(task, "{\"message\":\"No task with this id was found.\"}"));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("api/tasks")
    public ResponseEntity<String> createTask(@RequestBody Task task) throws ExecutionException, InterruptedException {
        _taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Task Created Successfully.\"}");
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("api/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) throws ExecutionException, InterruptedException {
        Task task = _taskService.getTaskById(id).get();
        if (task == null || !task.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"Could not delete the task.\"}");
        }

        _taskService.deleteTask(task);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Deleted Task Successfully.\"}");
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("api/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody Task task) {
        if (task == null || !task.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"Could not update the task.\"}");
        }

        _taskService.updateTask(task);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Task Updated Successfully.\"}");
    }

}
