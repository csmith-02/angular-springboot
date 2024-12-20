package com.connor.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private boolean completed;

    protected Task() {}

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        com.connor.Models.Task that = (com.connor.Models.Task) o;
        return completed == that.completed && Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, completed);
    }

    @Override
    public String toString() {
        return "TaskService{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}
