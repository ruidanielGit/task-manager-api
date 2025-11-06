package com.example.taskmanager.mappers;

import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO entityToTaskDto(Task task);

    Task taskDtoToEntity(TaskDTO taskDTO);
}
