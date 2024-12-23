import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormsModule, ReactiveFormsModule } from "@angular/forms"
import { TaskService } from '../services/task.service';
import { Task } from '../models/task';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css'
})
export class TasksComponent implements OnInit {
  
  formGroup: FormGroup;

  tasks: Task[] = [];

  constructor(private builder: FormBuilder, private service: TaskService){
    this.formGroup = this.builder.group({
      description: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.service.getTasks().subscribe(response=>{
      this.tasks = response;
    });
  }

  onSubmit(): void {
    let task: Task = this.formGroup.value;

    this.service.addTask(task).subscribe({
      complete: ()=>{
        console.log('in complete')
        this.service.getTasks().subscribe({
          next: (res) => {
            this.tasks = res
          },
          complete: () => {
            this.formGroup.reset();
          },
        });
      }
    })
  }

  onDelete(index: number): void {
    let task : Task = this.tasks[index];
    this.tasks.splice(index, 1);
    this.service.deleteTask(task.id);
  }
}
