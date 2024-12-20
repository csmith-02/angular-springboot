import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: '', loadComponent: ()=> import('./home/home.component').then(c=>c.HomeComponent)},
    { path: 'tasks', loadComponent: ()=> import('./tasks/tasks.component').then(c=>c.TasksComponent)},
    { path: 'tasks/:id', loadComponent: () => import('./task-details/task-details.component').then(c=>c.TaskDetailsComponent)},
    { path: '**',  loadComponent: () => import('./page-not-found/page-not-found.component').then(c=>c.PageNotFoundComponent)}
];
