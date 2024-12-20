package com.connor.Services;

import com.connor.Models.Task;
import com.connor.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {

    private final TaskRepository _taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        _taskRepository = taskRepository;
    }

    @Async
    public void createTask(Task task) {
        _taskRepository.save(task);
    }

    @Async
    public void updateTask(Task task) {
        Task retVal = _taskRepository.findById(task.getId()).orElse(null);
        if (retVal != null) {
            retVal.setDescription(task.getDescription());
            retVal.setCompleted(task.isCompleted());
            _taskRepository.save(retVal);
        }
    }

    @Async
    public CompletableFuture<List<Task>> getTasks() {
        return CompletableFuture.completedFuture(_taskRepository.findAll());
    }

    @Async
    public CompletableFuture<Task> getTaskById(Long id) {
        return CompletableFuture.completedFuture(_taskRepository.findById(id).orElse(null));
    }

    @Async
    public void deleteTask(Task task) {
        _taskRepository.delete(task);
    }
}
