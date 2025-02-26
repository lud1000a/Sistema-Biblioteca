package models;

import java.time.*;
import java.util.*;

import utils.BooksInfo;
import utils.BooksManagement;
import models.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Admin{
	
	public static void addBook(String id, String title, String author, String genres, String year, String sinopse) throws IOException {
		if(genres == null) {
			throw new RuntimeException();
		}
		String[] genreList = genres.split("-");
		Book newBook = new Book(id, title, genreList, author, year, sinopse);
		newBook.insertInDatabases();
	}
	
	//rentBook(Student student, String bookId)
	public void rentBook(String bookId, String bookName) throws IOException {
		//student.rent(String bookId);
		LocalDate today = LocalDate.now();
		BooksManagement.updateCredOut(bookId, today);
		BooksInfo.updateStock(bookName, -1);
	}
	
	public void devolveBook(String bookId, String bookName) throws IOException {
		//student.returnRented(String bookId);
		BooksManagement.removeCredOut(bookId);
		BooksInfo.updateStock(bookName, +1);
	}
}