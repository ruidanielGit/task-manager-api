package com.example.taskmanager;

import lombok.Data;

@Data
public class Task {

    private final int id;
    private final String title;
    private final String description;
    private final boolean done;
}


