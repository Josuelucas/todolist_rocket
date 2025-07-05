package br.com.industriamm.todolist.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data /* Utilizando o lombook para criar nosso gett & sett - código limpo */
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID") // Gerar ID de forma automatica
    private UUID id; // Ao inves de ter um id sequencial, temos o UUDI mais seguro.

    @Column (unique = true) // No banco de dados o usename será uma coluna com restrição que define um atributo unico.
    private String username;
    private  String  name;
    private  String  password;

    @CreationTimestamp
    private LocalDateTime createdAt; //  Definindo quando o dado foi criado no banco de dados


}
