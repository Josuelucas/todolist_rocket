package br.com.industriamm.todolist.task;


import br.com.industriamm.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
/*Marca toda a classe Java como um controlador REST. Ou seja, essa classe vai responder a requisições
HTTP (como GET, POST, PUT, DELETE).*/
@RequestMapping("/tasks")
/*Define uma rota base ou uma rota específica para métodos ou classes. Pode ser usada com todos os métodos
 HTTP: GET, POST, PUT, DELETE etc.*/
public class TaskController {

    @Autowired // Gerencias Instacia do Repositorio
    private ITaskRepository taskRepository;
    private List<TaskModel> tasks;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser =  request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        //10/11/2025 - current
        //10/10/2025 - startAt
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt()) ){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A data de inicio / data de termino deve ser maior do que a data atual");
            }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor que a data de termino");
        }
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser =  request.getAttribute("idUser");
        var task = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    } //Retornando todas as tarefas

    //http://localhost:8080/tasks/79849191-kansdoo-88848418
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id,  HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);

        if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada!");
        }

        var idUser =  request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alterar tarefa");
        }

        Utils.copyNullProperties(taskModel, task);
        var taskUpdated =  this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }

}
