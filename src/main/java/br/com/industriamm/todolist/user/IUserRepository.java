package br.com.industriamm.todolist.user;


import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;


public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username); // consulta (query) que busca um usuário pelo campo username.

}
