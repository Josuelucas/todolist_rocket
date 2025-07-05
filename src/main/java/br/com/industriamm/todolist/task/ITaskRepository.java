package br.com.industriamm.todolist.task;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public interface ITaskRepository extends JpaRepository <TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);

}
