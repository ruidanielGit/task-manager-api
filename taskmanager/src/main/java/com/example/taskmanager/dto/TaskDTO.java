package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(Long id, @NotBlank String title, String description, boolean done) {
}
