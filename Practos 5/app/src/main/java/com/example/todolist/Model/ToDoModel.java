package com.example.todolist.Model;


import androidx.annotation.NonNull;

import java.util.Objects;

public class ToDoModel {

    /*
        Класс ToDoModel
        Модель данных для работы
        с данными для базы данных

        Часть паттерна MVC
     */

    private String task, comment;
    private String dueDate, dueTime;
    private int id, status;


     // Конструктор класса ToDoModel
    public ToDoModel(){}

    /*
    Геттеры и сеттеры полей класса ToDoModel
     */

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    //Метод equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoModel toDoModel = (ToDoModel) o;
        return Objects.equals(id, toDoModel.id) &&
                Objects.equals(task, toDoModel.task) &&
                Objects.equals(comment, toDoModel.comment) &&
                Objects.equals(dueDate, toDoModel.dueDate) &&
                Objects.equals(dueTime, toDoModel.dueTime);
    }


    //Метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, task, comment, dueDate, dueTime);
    }

    //Метод toString
    @NonNull
    @Override
    public String toString() {
        return "ToDoData{" +
                "taskId='" + id + '\'' +
                ", task='" + task + '\'' +
                ", comment='" + comment + '\'' +
                ", date='" + dueDate + '\'' +
                ", time='" + dueTime + '\'' +
                '}';
    }


}
