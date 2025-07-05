package br.com.industriamm.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; // VAI EXECUTAR A APLICAÇÃO

@SpringBootApplication //Anotação que irá definir que o TodolistApplication é classe inicial
public class TodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

}
