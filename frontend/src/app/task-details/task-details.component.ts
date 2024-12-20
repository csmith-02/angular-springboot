import { Component, OnInit } from '@angular/core';
import { TaskService } from '../services/task.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from '../models/task';
import { Message } from '../models/message';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';


@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css'
})
export class TaskDetailsComponent implements OnInit {


  constructor(
    private service: TaskService,
    private route: ActivatedRoute,
    private router: Router,
    private builder: FormBuilder
  ){
    this.formGroup = this.builder.group({
      description: [this.description, [Validators.required]]
    })
  }

  task : Task | null = null;

  formGroup: FormGroup = new FormGroup({});

  errorMessage: string | null = null;

  description: String = ""

  ngOnInit(): void {

    let id = this.route.snapshot.paramMap.get("id");

    if (!id) {
      this.router.navigate(['/']);
      return;
    }

    let result = this.service.getTaskById(id!);
    result.subscribe({
      next: (response)=>{
        if ((response as Message).message) {
          this.errorMessage = (response as Message).message;
          return;
        }

        if (!(response as Task)) {
          console.log(response)
          this.errorMessage = "Invalid id parameter."
          return;
        }

        this.task = response as Task;
        this.description = this.task.description;
        this.formGroup.patchValue(this.task);
      },
      error: ()=>{
          this.errorMessage = `id: '${id}' is not a valid task id`;
      },
    });

  }

  onSubmit(): void {

    let task: Task = this.formGroup.value;
    task.id = this.task!.id;
    task.completed = this.task!.completed;
    this.service.updateTask(task).subscribe(response=>{
      this.router.navigate(['/tasks']);
    });
  }
}
