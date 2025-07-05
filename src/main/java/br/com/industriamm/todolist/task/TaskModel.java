package br.com.industriamm.todolist.task;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * Usuário(ID de Uusuário)
 *Descrição
 * Título
 * Data
 * Data Final
 * Prioridade
 * */
@Data // Criano gett e sett com Lombok
@Entity(name = "tb_task") // Definindo tabela
public class TaskModel {

    @Id // Definindo ID
    @GeneratedValue(generator = "UUID") // Gerando id AUTOMATICO
    private UUID id;
    private String description;

    @Column(length = 50) // Limitando quantidade de carácter do titulo
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp //Mapeando tarefa quando for criada
    private LocalDateTime createrAt;


    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw new Exception("O Campo Title deve conter no mínino 50 carácteres");
        }
        this.title = title;
    }

}
