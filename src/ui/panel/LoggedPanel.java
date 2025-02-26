package ui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import models.Book;
import utils.BooksInfo;
import java.io.IOException;

// Tela principal para o usuário logado
public class LoggedPanel extends JPanel{
    private JTextField searchField; // Campo de texto para inserir termos de pesquisa
    private JComboBox<String> genreFilter; // Caixa de seleção para filtrar livros por gênero
    private JButton sortButton; // Botão para ordenar os livros em ordem alfabética
    private DefaultListModel<String> listModel; // Modelo de lista que armazena os itens a serem exibidos (no caso, os livros)
    private JList<String> itemList; // Lista que exibe os livros disponíveis

    // Construtor PainelLogado
    public LoggedPanel(){

        setLayout(new BorderLayout());

        // Criando um painel para organizar título e painel de pesquisa
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Criando um painel separado para o título e centralizando ele
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Pesquisa de Itens no Acervo da Biblioteca");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(title);

        // Criando um painel separado para os filtros e botões
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        searchPanel.add(new JLabel("Pesquisar:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchPanel.add(new JLabel("Filtrar por Gênero:"));
        // Função do Banco de Dados para pegar os gêneros de lviros que existem
//        genreFilter = new JComboBox<>(getAvailableGenres().toArray(new String[0])); TODO FILTROS
//TODO        searchPanel.add(genreFilter);

        sortButton = new JButton("Ordenar A-Z");
        searchPanel.add(sortButton);

        topPanel.add(titlePanel);
        topPanel.add(searchPanel);

        // Adicionando o painel superior à interface principal (fixo no topo)
        this.add(topPanel, BorderLayout.NORTH);

        // Criando a lista de exibição dentro de um JScrollPane
        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(itemList);

        // Permitir que o scrollPane ocupe o espaço restante da tela automaticamente
        scrollPane.setMinimumSize(new Dimension(200, 300)); // Tamanho mínimo
        scrollPane.setPreferredSize(new Dimension(800, 600)); // Tamanho inicial
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Criando um painel central que cresce dinamicamente
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionando a lista ao painel principal
        this.add(listPanel, BorderLayout.CENTER);

        // Garantir que os componentes se expandam corretamente ao redimensionar a janela
        listPanel.setPreferredSize(new Dimension(getWidth(), getHeight()));

        updateItemList(); // Chama o metodo para preencher a lista com os livros disponiveis

        // Eventos: pesquisa automaticamente ao apertar "Enter" no campo de pesquisa
        
        searchField.addActionListener(e -> itemFilter());
//TODO        genreFilter.addActionListener(e -> itemFilter());
        sortButton.addActionListener(e -> sortItems());

        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selecionado = itemList.getSelectedValue();
                    if (selecionado != null) {
                        try {
							showBookPanel(selecionado);
						} catch (IOException e) {
							e.printStackTrace();
						}
                    }
                }
            }
        });
    }

    // Metodo para exibir o painel dos livros
    private void showBookPanel(String title) throws IOException {
        // Função para obter os detalhes do livro do banco de dados, função pra verificar se o livro já esta alugado e função pra verificar multa
        String book = BooksInfo.returnLine(title);
        String[] infos = book.split(",");
//        boolean livroJaAlugado = book;
//TODO        boolean possuiMulta = FuncaoDoBancoDados.verificarMultaUsuario();

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detalhes do Livro", true);
        dialog.setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailsPanel.add(new JLabel("Título: " + infos[0]));
        detailsPanel.add(new JLabel("Autor: " + infos[1]));
        detailsPanel.add(new JLabel("Gênero: " + infos[3]));
        detailsPanel.add(new JLabel("Ano de Publicação: " + infos[2]));

//        JPanel buttonPanel = new JPanel();
//        JButton rentButton = new JButton("Alugar");
//TODO        JButton renewButton = new JButton("Renovar");

//        rentButton.setEnabled(!livroJaAlugado && !possuiMulta);
//        renewButton.setEnabled(livroJaAlugado && !possuiMulta);
//
//        rentButton.addActionListener(e -> {
//            if (!possuiMulta) {
////                FuncaoDoBancoDados.alugarLivro(livroSelecionado);
//                JOptionPane.showMessageDialog(dialog, "Livro alugado com sucesso por 30 dias!", "Aluguel Confirmado", JOptionPane.INFORMATION_MESSAGE);
//                dialog.dispose();
//            }
//        });
//
//        renewButton.addActionListener(e -> {
//            if (!possuiMulta) {
// //               FuncaoDoBancoDados.renovarLivro(livroSelecionado);
//                JOptionPane.showMessageDialog(dialog, "Livro renovado por mais 30 dias!", "Renovação Confirmada", JOptionPane.INFORMATION_MESSAGE);
//                dialog.dispose();
//            }
//TODO        });

//        buttonPanel.add(rentButton);
//TODO        buttonPanel.add(renewButton);

        dialog.add(detailsPanel, BorderLayout.CENTER);
//TODO        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Metodo que chama a função do banco para obter os gêneros armazenados
    private List<String> obterGenerosDisponiveis() {
        List<String> generos = new ArrayList<>(Arrays.asList("Fantasia","Sci-Fi","Romance","Medieval","Comedia")); // Chama a função responsável pelo banco de dados
        generos.add(0, "Todos"); // Adiciona "Todos" como primeira opção
        return generos;
    }


    // metodo para atualizar a lista de livros
    private void updateItemList(){
        listModel.clear();
        try {
        	 List<String> livros = getBooks();
             for (String livro : livros) {
                 listModel.addElement(livro);
             }
        }catch(IOException ioe) {
        	JOptionPane.showMessageDialog(this, ioe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
       
    }

    private List<String> getBooks() throws IOException {

        // Chama a função que retorna a lista de livros armazenados no banco de dados

        List<String> listBooks = BooksInfo.getAllBooks();

        List<String> formatedBooks = new ArrayList<>();
        for (String book : listBooks) {
        	
        	System.out.println(book);
        	String[] bookFormat = book.split(","); 
        	if(bookFormat[0].isEmpty()) {
        		continue;
        	}
            // Formatar para "Título do Livro, Autor (Gênero) - Ano de Publicação: Ano"
            String formatedBook = String.format("%s, %s (%s) - Ano de Publicação: %s - disponiveis: %s",
                    bookFormat[0],
                    bookFormat[1],
                    bookFormat[3],
                    bookFormat[2],
                    bookFormat[5]);
            formatedBooks.add(formatedBook);
        }

        return formatedBooks;
    }


    private void itemFilter(){
        String searchTerm = searchField.getText().toLowerCase();
        String genreSelected = genreFilter.getSelectedItem().toString();

        listModel.clear();

        try{
        	
        	List<String> livros = getBooks();
            for (String livro : livros) {
                 if ((genreSelected.equals("Todos") || livro.toLowerCase().contains(genreSelected.toLowerCase()))
                         && livro.toLowerCase().contains(searchTerm)) {
                     listModel.addElement(livro);
                 }
             }
        }
        catch(IOException ioe) {
        	JOptionPane.showMessageDialog(this, ioe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
       
    }

    private void sortItems() {
        List<String> sortedItems = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            sortedItems.add(listModel.getElementAt(i));
        }

        // ordena os itens alfabeticamente
        Collections.sort(sortedItems);
        listModel.clear();
        for (String item : sortedItems) {
            listModel.addElement(item);
        }
    }
}