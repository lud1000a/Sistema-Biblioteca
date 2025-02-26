package utils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import models.Book;
// atributos: name, author, publishYear, sinopse, stock
public class BooksInfo{
	static String path = "src//database//booksInfo.csv";
	
	public static void updateStock(String bookName, int sum) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(bookName.equals(array[0])) {
				int newStock = Integer.parseInt(array[5]) + sum;
				System.out.println(newStock);
				array[5] = String.valueOf(newStock);
				CSV.updateDataById(array,path);
				break;
			}
		}
		reader.close();
	}
	
	public static void insert(List<String> data) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		int stock = 0;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(data.get(0).equals(array[0])) {
				stock = Integer.parseInt(array[5]);
				break;
			}
		}
		reader.close();
		
		stock = stock + 1;
		data.add(String.valueOf(stock));
		
		String[] dataAsArray = data.toArray(new String[data.size()]);
		if(stock == 1) {
			CSV.insertInTable(dataAsArray, path);
		}else {
			CSV.updateDataById(dataAsArray, path);
		}
	}
	
	public static List<String> getAllBooks() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = "";
		List<String> arrayLine = new ArrayList<>();
		while((line = reader.readLine()) != null) {
			arrayLine.add(line);
		}
		reader.close();
		return arrayLine;
	}
	
	public static String returnLine(String name) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(array[0] == name) {
				reader.close();
				return line;				
			}
		}
		reader.close();
		return null;
	}
}