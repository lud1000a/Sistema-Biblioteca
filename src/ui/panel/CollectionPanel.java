package ui.panel;

import javax.swing.*;
import models.Admin;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Aqui será a parte responsável pelo painel que mostra o acervo da biblioteca. Esse painel somente irá aparecer para administradores.
public class CollectionPanel extends JPanel{

    private JTable bookTable;
    private DefaultTableModel tableModel;

        public CollectionPanel() {
        setLayout(new BorderLayout());

        // Creating panel for buttons at the top
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBookButton = new JButton("Adicionar Livro");
        JButton removeBookButton = new JButton("Remover Livro");
        topPanel.add(addBookButton);
        topPanel.add(removeBookButton);
        add(topPanel, BorderLayout.NORTH);

        // Creating table to display books
        String[] columns = {"Título", "Autor", "Gênero", "Ano de Publicação"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        updateBookList();
    }

    private void updateBookList() {
        tableModel.setRowCount(0);
//        List<Book> books = DatabaseFunctions.getBookList(); // Retrieves all books from the database
//
//        for (Book book : books) {
//            Object[] row = {
//                    book.getTitle(),
//                    book.getAuthor(),
//                    book.getGenre(),
//                    book.getPublicationYear()
//            };
//            tableModel.addRow(row);
//TODO        }
    }

    private void addBook() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField idField = new JTextField();
        JTextField sinopseField = new JTextField();

        Object[] fields = {
                "Título:", titleField,
                "Id", idField,
                "Autor:", authorField,
                "Gênero:", genreField,
                "Ano de Publicação:", yearField,
                "Sinopse:", sinopseField
                
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Adicionar Livro", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
				Admin.addBook(idField.getText(),titleField.getText(), authorField.getText(), genreField.getText(),yearField.getText(),sinopseField.getText());
				JOptionPane.showMessageDialog(this, "LIVRO CRIADO COM SUCESSO!!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
            updateBookList();
        }
    }

    private void removeBook() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField yearField = new JTextField();

        Object[] fields = {
                "Título:", titleField,
                "Autor:", authorField,
                "Gênero:", genreField,
                "Ano de Publicação:", yearField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Remover Livro", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
 //TODO           DatabaseFunctions.removeBook(titleField.getText(), authorField.getText(), genreField.getText(), Integer.parseInt(yearField.getText()));
            updateBookList();
        }
    }
    
}