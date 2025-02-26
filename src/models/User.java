package models;

import java.io.IOException;

import exceptions.StudentNotFoundException;
import utils.CSV_STUDENTS;

public class User {
	
	// Funcao para login do usuario
	// Com excecao de usuario nao encontrado 
	public static void login(String id, String password) throws IOException {	
		boolean flag = false;
		if(CSV_STUDENTS.loginExists(id, password)) {
			flag = true;
			System.out.println("LOGIN FEITO COM SUCESSO");
		}
		if(!flag) {
			throw new StudentNotFoundException("O USUARIO NAO FOI ENCONTRADO");  // Caso nao encontre nenhum aluno com id e senha informados, 
		}                                          							  // gera um exceptions de aluno nao encontrado
	}
	
	public static boolean isAdmin(String id) {
		boolean flag = false;
		if(id.contains("#")) {
			flag = true;
		}
		return flag;
	}
}
