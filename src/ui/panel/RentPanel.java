// RentPanel.java
package ui.panel;

import javax.swing.*;
import models.Book;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import models.Student;

// Uma tela dedicada aos itens alugados pelo usuário
// Após um item ser alugado, deverá aparecer aqui o nome do item e a data do começo e do fim do aluguel.
public class RentPanel extends JPanel{
	private Student loggedUser;
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public RentPanel(Student loggedUser){
        setLayout(new BorderLayout());
        this.loggedUser = loggedUser;

        JLabel title = new JLabel("Histórico de Aluguéis", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Título", "Autor", "Gênero", "Ano de publicação", "Dia do Aluguel"};
        tableModel = new DefaultTableModel(columns, 0);
        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

      
        updateHistory();
    }


    public void updateHistory() {
//        tableModel.setRowCount(0);
//        List<Book> history = DatabaseFunctions.getUserRentalHistory();
//
//        for (RentedBook book : history) {
//            Object[] row = {
//                    book.getTitle(),
//                    book.getAuthor(),
//                    book.getGenre(),
//                    book.getPublicationYear(),
//                    book.getRentalDate()
//            };
//            tableModel.addRow(row);
//TODO        }
    }
}