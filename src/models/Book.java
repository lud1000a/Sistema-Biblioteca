package models;

import java.io.IOException;
import java.util.*;

import utils.*;


public class Book {
	String id;
	String name;
	String[] genres;
	String author;
	String year;
	String sinopse;
	String stock;

	public Book(String id, String name, String[] genres, String author, String year, String sinopse) {
		this.id = id;
		this.name = name;
		this.genres = genres;
		this.author = author;
		this.year = year;
		this.sinopse = sinopse;
	}
	public String getId() {
		return id;
	}
//	public Book(String name) throws IOException {
//		this.name = name;
//		String[] databaseLine = BooksInfo.returnLine(name);
//		if(databaseLine != null) {
//			this.author = databaseLine[1];
//			this.year = databaseLine[2];
//			this.genres = databaseLine[3].split("-");
//			this.sinopse = databaseLine[4];
//			this.stock = databaseLine[5];
//		}
//	}
	
	public void	insertInDatabases() throws IOException {
		//BooksManagement.add(this.id, this.name, this.year);
		String genresAsString = String.join("-", this.genres);
		List<String> booksInfo = new ArrayList<>(Arrays.asList(this.name, this.author, this.year, genresAsString, this.sinopse));
 		BooksInfo.insert(booksInfo);	
 		List<String> booksManagement = new ArrayList<>(Arrays.asList(this.id, this.name));
 		BooksManagement.insert(booksManagement);
	}
	
	public String getName() {
		return this.name;
	}
	public String getAuthor() {
		return this.author;
	}
	public String getYear() {
		return this.year;
	}
	public String getGenres() {
		if(this.genres != null) {
			return String.join("-", this.genres);
		}
		return null;
	}
	public String getStock() {
		return this.stock;
	}
}
