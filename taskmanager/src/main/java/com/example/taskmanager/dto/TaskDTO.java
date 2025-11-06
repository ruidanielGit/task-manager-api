package com.example.taskmanager.dto;

import lombok.Getter;

@Getter
public class TaskDTO {

    private int id;
    private String title;
    private String description;
    private boolean done;
}
