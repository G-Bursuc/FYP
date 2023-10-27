package com.example.timemanagement;

public class TaskToDo {
    String name;
    String desc;
    String dueDate;
    boolean complete;

    public TaskToDo() {
    }

    public TaskToDo(String n, String d, String dd, boolean c) {
        this.name = n;
        this.desc = d;
        this.dueDate = dd;
        this.complete = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
