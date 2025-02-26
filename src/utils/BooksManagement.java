package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import models.Book;

// atributos: id, name, checkOut, deadline
public class BooksManagement{
	static String path = "src//database//booksManagement.csv";
	
	public static void insert(List<String> data) throws IOException {
		data.add("x");
		data.add("x");
		String[] asArray = data.toArray(new String[data.size()]);
		CSV.insertInTable(asArray, path);
	}
	
	public static void removeCredOut(String bookId) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(array[0].equals(bookId)) {
				array[2] = "x";
				array[3] = "x";
				CSV.updateDataById(array, path);
				break;
			}
		}
		reader.close();
	}
	
	public static void updateCredOut(String bookId,LocalDate date) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(array[0].equals(bookId)) {
				array[2] = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				int daysCount = 0;
				while(daysCount < 30) {
					date = date.plusDays(1);
					if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
						daysCount++;
					}
				}
				array[3] = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				CSV.updateDataById(array, path);
				break;
			}
		}
		reader.close();
	}
	
	public static double getTotalFees(String[] bookIds) throws IOException {
		double total = 0;
		for(String bookId : bookIds) {
			total += getOverdue(bookId) * 1.00;
		}
		return total;
	}
	
	public static long getOverdue(String bookId) throws IOException {
		long businessDays = -1;
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(array[0].equals(bookId)) {
				String[] date = array[2].split("/");
		        LocalDate begin = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
		        LocalDate end = LocalDate.now(); 
		        while (!begin.isAfter(end)) {
		            if (begin.getDayOfWeek() != DayOfWeek.SATURDAY && begin.getDayOfWeek() != DayOfWeek.SUNDAY) {
		                businessDays++;
		            }
		            begin = begin.plusDays(1);
		        }
			}
		}
		reader.close();
		if(businessDays <= 30) {
			return 0;
		}
		else {
			return businessDays - 30;
		}
	}
	
	public static List<Book> search(String searched) throws IOException{
		List<Book> options = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(path));		
		String line = null;
		while((line = reader.readLine()) != null) {
			String[] array = line.split(",");
			if(array[0].contains(searched)) {
				options.add(new Book(array[0]));
				break;
			}
		}
		reader.close();
		return options;
	}
}