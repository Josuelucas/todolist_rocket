package br.com.industriamm.todolist.user;


import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *Modificadores - Tipos de acessos
 * public
 * private
 * protected
 */
@RestController
/*Marca toda a classe Java como um controlador REST. Ou seja, essa classe vai responder a requisições
HTTP (como GET, POST, PUT, DELETE).*/
@RequestMapping("/users")
/*Define uma rota base ou uma rota específica para métodos ou classes. Pode ser usada com todos os métodos
 HTTP: GET, POST, PUT, DELETE etc.*/
public class UserController {

    @Autowired // Irá gerenciar ou instanciar todo o ciclo de vida
    private IUserRepository userRepository; // acessar o banco de dados

    /**
     *String
     *Integer (int) numeros interior
     * Double (double) numero 0.0000  om casa decimais
     * Float (float) Número 0.000
     * char (A C )
     * DATE (data)
     * void - não tem retorno do método
     */
    /*
    * Body // @RequestBody - Essas informações irão vir do body
    * */
    @PostMapping("/") //Define que esse método será chamado quando uma requisição HTTP POST for feita para a URL "/"..
    //Esse método é chamado quando alguém envia uma requisição POST para "/".
    public ResponseEntity create(@RequestBody UserModel userModel) {
       var user = this.userRepository.findByUsername(userModel.getUsername()); // Verifica no banco de dados se já existe um usuário com o mesmo nome de usuário (username).

       if(user  != null){
           //Mnesagem de erro
           //Status Code - 500
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
       } //Se user for diferente e null, significa que o usuario já exisitir.

        var passwordHashred = BCrypt.withDefaults()
         .hashToString(12, userModel.getPassword().toCharArray()); // Criptografando a senha do usuario .

        userModel.setPassword(passwordHashred);

       var userCreated = this.userRepository.save(userModel); // Se o usuário não existir, salva o novo no banco de dados.
       return ResponseEntity.status(HttpStatus.OK).body(userCreated); // Caso não tenha o usurario, mostrará created.
    }
}
