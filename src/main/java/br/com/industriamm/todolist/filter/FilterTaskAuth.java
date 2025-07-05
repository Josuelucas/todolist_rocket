package br.com.industriamm.todolist.filter;



import java.io.IOException;
import java.util.Base64;


import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.industriamm.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.table.TableRowSorter;

@Component // Fazendo com que o próprio Spring Gererencie
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{

        var ServletPath = request.getServletPath();
            if(ServletPath.startsWith("/tasks/")) {
                //Irá pegar a autenticação (usuario e senha)
                var authorization = request.getHeader("Authorization");

                var authEncoded = authorization.substring("Basic".length()).trim(); // Pegando o basic vendo quantidade de carácter, após remova todos os espaços

                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);

                var authString = new String(authDecoded);

                // ["josuelucas", "12345"]
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];
                System.out.println("Authorization");
                System.out.println(username);
                System.out.println(password);
                //Validar Usuário
                var user = this.userRepository.findByUsername(username);
                if (user == null) {
                    response.sendError(401);
                } else {

                    //Validar Senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (passwordVerify.verified) {
                        //Segue Viagem
                        request.setAttribute("idUser", user.getId());
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }

                }
            }else{
                filterChain.doFilter(request, response);
            }
    }
}
