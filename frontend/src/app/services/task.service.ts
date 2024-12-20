import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Task } from '../models/task';
import { Observable } from 'rxjs';
import { Message } from '../models/message';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http: HttpClient) {}

  getTasks() : Observable<Task[]> {
    return this.http.get<Task[]>("http://localhost:8080/api/tasks");
  }

  addTask(task: Task): Observable<Task> {
    task.completed = false;
    return this.http.post<Task>("http://localhost:8080/api/tasks", task);
  }

  deleteTask(id: number): void {
    this.http.delete(`http://localhost:8080/api/tasks/${id}`).subscribe();
  }

  getTaskById(id: string): Observable<Task|Message> {
    return this.http.get<Task|Message>(`http://localhost:8080/api/tasks/${id}`);
  }

  updateTask(task: Task): Observable<Task> {
    return this.http.put<Task>(`http://localhost:8080/api/tasks/${task.id}`, task);
  }
}
