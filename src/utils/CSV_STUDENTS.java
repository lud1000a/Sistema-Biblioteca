package utils;

import java.io.*;
import java.util.*;

public abstract class CSV_STUDENTS{
	private static String filePath = "src\\database\\user.csv";


//  METODOS CRUD (CREATE - READ - UPDATE - DELETE ) --------------------------------------------------------------------------
	
	// CREATE - Adiciona elementos ao banco de dados ------------------------------------------------------------------------
	
	// O metodo insert(String[]) recebe um array com os dados do aluno que está sendo adicionado
	public static void insert(String[] lineData) throws IOException{	
		//Criamos um objeto que escreve no CSV, escrevemos uma linha e pulamos para a proxima,
		//deixando um espaço para proxima palavra que queiramos adicionar
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
		String line = String.join(",", lineData); // como o CSV é separado em virgulas, juntamos novamente a palavra
		writer.write(line);                       // com virgulas entre elas
		writer.newLine();
		writer.close();
	}
	
	//------------------------------------------------------------------------------------------------------------------------
	
	// READ - Retorna informacoes sobre os elementos no banco de dados -------------------------------------------------------
	
	// Metodo que retorna as informacoes de um aluno pelo seu id
	public static String[] getById(String id) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		String[] data = null;
		while((line = reader.readLine()) != null) {
			String[] lineArray = line.split(",");
			if (lineArray[0].equals(id)) {
				data = lineArray;
			}
		}
		return data;
	}
	
	// Metodo que retorna se as informacoes de login que o aluno inseriu existe
	public static boolean loginExists(String id, String password) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		boolean flag = false;  //Valor que vai ser retornado, comeca como false pois o aluno nao foi encontrado
		while((line = reader.readLine()) != null) {
			String[] lineArray = line.split(",");
			if (lineArray[0].equals(id) && lineArray[2].equals(password)) {
				flag = true;   //Caso o aluno seja encontrado, altera o valor para true
			}
		}
		return flag;
	}
	
	// Metodo que verifica se o aluno ja foi registrado no banco de dados
		public static Boolean isIncluded(String id) throws IOException{
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] lineArray = line.split(",");
				if (lineArray[0].equals(id)) {
					reader.close();
					return true;
				}
			}
			reader.close();
			return false;
		}
		
	//------------------------------------------------------------------------------------------------------------------
		
	// UPDATE - Atualiza os dados dos alunos no banco de dados ----------------------------------------------------------
	
	//Atualiza os dados de um aluno apartir de seu id, reescreve o banco de dados com a alteracao da linha do aluno
	public static void updateDataById(String[] updatedData) throws IOException {
		
		//Listas que nao vao sofrer alteracoes
		List<String> database = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		while((line = reader.readLine()) != null) {
			String[] lineArray = line.split(",");
			if(lineArray[0].equals(updatedData[0])) {                    //UpdatedData[0] corresponde ao valor do id do aluno
				database.add(String.join(",", updatedData));             //Faz a substituicao dos dados antigos do alugo pelos novos no novo database
			}else {
				database.add(line);
			}
		}
		reader.close();
		
		//Ao criarmos esse writer, colocamos um false no seu segundo parametro para abrir
		//O csv apagando todos os dados anteriores
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
				
		//Logo em seguida, com a lista de linhas que queremos manter, adicionamos novamente
		//Uma a uma
		for(int i = 0; i < database.size(); i++) {
			writer.write(database.get(i));
			writer.newLine();
		}
		writer.close();
	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	// DELETE - Metodos que fazem a remocao de informacoes do banco de dados -------------------------------------------
	
	// metodo void eraseById(String) deleta as informacoes de um aluno pelo seu id
	public void eraseById(String id) throws IOException{
		
		// devemos salvar todas as linhas que nao sao aquelas que queremos apagar
		List<String> maintain = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		while((line = reader.readLine()) != null) {
			
			// a linha recebe um split, vemos se a coluna das matriculas é igual a 
			// que queremos, caso nao, salvamos na lista das palavras que queremos manter,
			// só que novamente com as virgulas.
			// por que separar?
			// resposta: robustez. em um caso especifico que a nossa matricula se pareça com
			// alguma id ou esteja contida nela, caso nao compararmos especificamente com a
			// coluna dá matricula, pode gerar um erro.
			String[] lineArray = line.split(",");
			if (lineArray[0] != id) {
				maintain.add(line);
			}
		}
		reader.close();
		// caso nao haja nenhuma matricula igual a entrada, ocorre um erro 
		// TODO criar excecao para quando nao encontra nada pra apagar
		if(maintain.size() == 0) {
			System.out.println("nenhuma matricula encontrada");
			return;
		}
		
		// ao criarmos esse writer, colocamos um false no seu segundo parametro para abrir
		// o csv apagando todos os dados anteriores
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
		
		// logo em seguida, com a lista de linhas que queremos manter, adicionamos novamente
		// uma a uma
		for(int i = 0; i < maintain.size(); i++) {
			writer.write(maintain.get(i));
			writer.newLine();
		}
		writer.close();
	}
	
	//-------------------------------------------------------------------------------------------------
}