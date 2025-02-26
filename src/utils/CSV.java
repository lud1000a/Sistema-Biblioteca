package utils;
import java.io.*;
import java.util.*;

public class CSV{
	private static String filePath = "src//database//booksManagement.csv";
	
	public static String[] returnLineByIndex(String data, int index) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filePath));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(array[index] == data) {
				reader.close();
				return array;				
			}
		}
		reader.close();
		return null;
	}
	
	// void printAllTable() serve meramente para teste, mas ela pode ser mudada para carregar
	// as informações de uma janela da interface grafica com algumas alterações
	public static List<String> getAllTable() throws IOException{
		
		// nao existe mais a necessidade de try catch nas funçoes pq o construtor ja testa se ta tudo bem com o
		// arquivo.
		// analise de risco: até entao nao deu nenhum problema fazer dessa forma, mas futuramente, caso haja um,
		// basta usar o try catch como no construtor
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		// essa line é uma dummy que vai segurar os valores das linhas do nosso csv
		String line = "";
		List<String> arrayLine = new ArrayList<>();
		// traduzindo: enquanto houverem linhas "nao vazias", segue-se fazendo a leitura
		while((line = reader.readLine()) != null) {
			arrayLine.add(line);
		}
		reader.close();
		return arrayLine;
	}
	
	// o metodo insertInTable(String[]) recebe um array com os dados do aluno que está sendo adicionado
	public static void insertInTable(String[] lineData, String path) throws IOException{
		
		// caso esse aluno ja esteja dentro dos cadastrados, nada se faz
		// TODO criar exception
		// caso contrario, criamos um objeto que escreve no CSV, escrevemos uma linha e pulamos para a proxima,
		// deixando um espaço para proxima palavra que queiramos adicionar
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		String line = String.join(",", lineData); // como o CSV é separado em virgulas, juntamos novamente a palavra
		writer.write(line);                       // com virgulas entre elas
		writer.newLine();
		writer.close();
	}
	
	// funcao que pesquisa se a palavra já está no CSV
	static Boolean alreadyIn(String data, String path) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = "";
		while((line = reader.readLine()) != null) {
			String[] lineArray = line.split(",");
			if (lineArray[0] == data) {
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}
	
	// metodo void eraseByRegistration(String) só dá uma olhada e retorna se 
	// a matricula ja está la ou nao
	public static void eraseByIndex(String data, int index) throws IOException{
		
		// devemos salvar todas as linhas que nao sao aquelas que queremos apagar
		List<String> maintain = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = "";
		while((line = reader.readLine()) != null) {
			String[] lineArray = line.split(",");
			if (lineArray[index] != data) {
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
	
	public static void updateDataById(String[] updatedData, String path) throws IOException {
		
		//Listas que nao vao sofrer alteracoes
		List<String> database = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = "";
		while((line = reader.readLine()) != null) {
			String[] lineArray = line.split(",");
			if(lineArray[0].equals(updatedData[0])) {                    //updatedData[0] corresponde ao valor do id do aluno
				database.add(String.join(",", updatedData));             //faz a substituicao dos dados antigos do alugo pelos novos no novo database
			}else {
				database.add(line);
			}
		}
		reader.close();
		
		// ao criarmos esse writer, colocamos um false no seu segundo parametro para abrir
		// o csv apagando todos os dados anteriores
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
				
		// logo em seguida, com a lista de linhas que queremos manter, adicionamos novamente
		// uma a uma
		for(int i = 0; i < database.size(); i++) {
			writer.write(database.get(i));
			writer.newLine();
		}
		writer.close();
	}
}