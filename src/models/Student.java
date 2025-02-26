package models;

import java.io.*;
import java.util.*;
import exceptions.RentedNotFoundException;
import exceptions.StudentAlreadyRegisteredException;
import exceptions.StudentNotFoundException;
import utils.CSV_STUDENTS;
public class Student {
	private String id;  // No caso a matricula
	private String name;  // Nome do aluno
	private String password;  // Senha do aluno
	private List<String> rentedIds;  // Ids dos livros que ele pegou 
	private float oldFine;
	
	
//	Metodos construtores -----------------------------------------------------------------------------------------------------
	
	// Metodo construtor para criar um novo aluno
	public Student(String id,String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.rentedIds = null; // O aluno comeca sem nenhum Id de livro alugado
	}
	
	// Metodo construtor para puxar um aluno do banco de dados
	public Student(String id) throws IOException {
		String[] data = CSV_STUDENTS.getById(id);  // Puxa os dados do aluno do banco seguindo o padrao id,name,password,rentedIds
		if(data == null) {
			throw new StudentNotFoundException("ALUNO NAO ENCONTRADO");  // Caso nao encontre o aluno no banco de dados, gera exceptions
		}
		this.id = id;
		this.name = data[1];
		this.password = data[2];
		
		// Caso o aluno nao possua livros ja alugados, devolve um array vazio para rentedIds,
		// caso ja haja algum livro alugado, manda uma lista com esses livros
		if(data[3].equals("null")) {
			this.rentedIds = new ArrayList<String>(Arrays.asList());
		}else {
			String[] rentedIds = data[3].split("-");
			this.rentedIds = new ArrayList<String>(Arrays.asList(rentedIds));
		}
		
	}
	
// 	-------------------------------------------------------------------------------------------------------------------------
	
//	Funcoes de login e registro ---------------------------------------------------------------------------------------------
/*	Aqui sao implementadas as funcoes relaciondas ao login e registro dos alunos,
 *  acompanhadas de suas respectivas exceptions.*/
	
	// Funcao para registrar o aluno static
	public static void register(String id, String name, String password) throws IOException {
		if(CSV_STUDENTS.isIncluded(id)) {  
			throw new StudentAlreadyRegisteredException("ESTUDANTE JA REGISTRADO"); // Caso o id do aluno ja esteja incluido no banco de dados, 
		}   											   // gera uma exceptions de aluno ja registrado
		Student student = new Student(id, name, password);	
		student.addToDatabase();
		System.out.println("Student cadastrado com sucesso");
	}
	
//	 Funcao para login do aluno
//	 Com excecao de aluno nao encontrado 
	public static Student login(String id, String password) throws IOException {
		Student student = null;
		
		if(CSV_STUDENTS.loginExists(id, password)) {
			student = new Student(id);
			System.out.println("LOGIN FEITO COM SUCESSO");
		}
		if(student == null) {
			throw new StudentNotFoundException("O ALUNO NAO FOI ENCONTRADO");  // Caso nao encontre nenhum aluno com id e senha informados, 
		}                                          // gera um exceptions de aluno nao encontrado

		return student;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
	
//  Funcoes Relacionadas ao banco de dados ----------------------------------------------------------------------------------
/* 	Os metodos definidos nessa secao sao apenas para facilitar a leitura do codigo
 *  e sao utilizados por outras partes do codigo, nao sendo utilizados diretamente.
 *  (caso fossem, causariam problemas como repeticoes de dados registrados no banco,
 *  por isso sao privados).
 *  Logo, as exceptions relacionadas a eles sao feitas nessas outras partes,
 *  como no metodo estatico register. */
 
	// Envia os dados do aluno para o banco de dados
	private void addToDatabase() throws IOException{
		CSV_STUDENTS.insert(getData());
	}
	
	// Atualiza os dados do aluno no banco de dados
	private void updateDatabase() throws IOException{
		CSV_STUDENTS.updateDataById(getData());
	}
	
	// Faz um array de strings com os dados do aluno
	public String[] getData() {
		String rentedIds = null;
		if(!(this.rentedIds == null)) { //So faz a uniao dos ids caso haja algum
			rentedIds = String.join("-", this.rentedIds);
		}
		
		String[] data = {this.id, this.name, this.password, rentedIds};
		
		return data;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------
	
//  Aluguel, Multa e Devolucao de livros -------------------------------------------------------------------------------------
	
	// TODO implementar funcao nos livros para trocar estado entre alugado e disponivel e somar o valor da multa acumulada
	// Exception para nao permitir um aluno devolver um livro que nao tem DONE
	public void returnRented(String id) throws IOException {
		// TODO calcular valor da multa no livro
		if(!this.rentedIds.contains(id)) {
			throw new RentedNotFoundException();
		}
		this.rentedIds.remove(id);
		// Caso a lista for ficar vazia, adiciona null 
		if(this.rentedIds.isEmpty()) {
			this.rentedIds.add("null"); 
		}
		this.updateDatabase(); // Atualiza a lista de livros no banco de dados
	}
	
	// Funcao para alugar Livro
	// TODO funcao para que nao possa alugar caso a multa total seja !=0
	// TODO implementar exceptions caso o livro nao seja alugavel
	public void rent(String id) throws IOException{
		if(this.rentedIds.contains(id)) {
			throw new IOException("O ALUNO JA PEGOU O LIVRO");
		}else {
			this.rentedIds.add(id);
			this.updateDatabase();
		}
		
	}
	
	// Funcao para calcular valor total da multa do aluno
	// TODO fazer funcao para calcular multa dos livros atrasados
	public float totalFees() {
	//	float totalFine = this.oldFine + calculateFine();
		return 0;
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------------
	
//	Gets e Sets ----------------------------------------------------------------------------------------------------------------
	public boolean isAdmin() {
		if(this.id.equals("#30#")) {
			return true;
		}
		return false;
	}
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	// Metodo para obter a lista de livros alugados do aluno
	public List<String> getRentedIds() {
		//Caso a lista so possua o elemento null default, nao retorna nada
		if(this.rentedIds.get(0).equals("null")) {
			throw new RentedNotFoundException();
		}
		return this.rentedIds;
	}
}