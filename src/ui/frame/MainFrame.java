package ui.frame;

import javax.swing.*;
import models.Student;
import java.awt.*;
import ui.panel.*;

// Nesta classe, será gerenciada toda a interface gráfica.
// A alternância entre diferentes telas (login, cadastro, multas, alugueis) será feito com CardLayout.
// A barra de menu lateral também será gerenciada aqui.
public class MainFrame extends JFrame{
    private CardLayout cardLayout; // O gerenciador de layouts
    private JPanel mainPanel;  // O painel que possui todas as telas.
    private BarMenu barMenu;  // O menu vertical.
    private Student loggedUser; // Usuario logado TODO trocar para adm e aluno
    

    public MainFrame(){
        configFrame();
        initComponents();
    }

    // Método que irá configurar as caracterísitcas básicas da janela.
    private void configFrame(){
        setTitle("Biblioteca UFC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // Esse metodo irá inicializar e organizar os componentes gráficos.
    private void initComponents(){
        // Painel principal com layout de borda (barra lateral + conteúdo).
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Configura a barra lateral que ficará à esquerda.
        barMenu = new BarMenu(this);
        contentPanel.add(barMenu, BorderLayout.WEST);

        // Configura o sistema de telas (CardLayout).
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Aqui, iremos adicionar todas as telas desejadas.
        mainPanel.add(new InitialPanel(), "inicio");
        mainPanel.add(new LoginPanel(this), "login");
        mainPanel.add(new LoggedPanel(), "logado");
        mainPanel.add(new RegisterPanel(this), "cadastro");
        mainPanel.add(new RentPanel(loggedUser), "alugueis");
        mainPanel.add(new FeePanel(loggedUser), "multas");
        mainPanel.add(new CollectionPanel(), "acervo");
        mainPanel.add(new AccountsPanel(), "contas");


        contentPanel.add(mainPanel, BorderLayout.CENTER);
        setContentPane(contentPanel);
    }

    // Este metodo serve para alternar as telas disponíveis
    public void changePanel(String panelName){
        refreshPanels();
        cardLayout.show(mainPanel, panelName);
        System.out.println(mainPanel.getComponentCount());
    }

    // Este metodo irá atualizar a barra de menu lateral.
    public void updateMenuBar(BarMenu newBar){
        getContentPane().remove(barMenu);
        barMenu = newBar;
        getContentPane().add(barMenu, BorderLayout.WEST);
        revalidate();
        repaint();
    }

    // Os próximos 2 metodos são metodos de acesso.
    public BarMenu getBarMenu(){
        return this.barMenu;
    }

    // Método para setar a variável usuário Logado em usuario. utilizando o id do usuario
    public void setLoggedUser(Student user){
        this.loggedUser = user;
    }
    
    public void refreshPanels() {
    	//Recarrega os paineis
    	mainPanel.removeAll();
    	mainPanel.add(new InitialPanel(), "inicio");
        mainPanel.add(new LoginPanel(this), "login");
        mainPanel.add(new LoggedPanel(), "logado");
        mainPanel.add(new RegisterPanel(this), "cadastro");
        mainPanel.add(new RentPanel(loggedUser), "alugueis");
        mainPanel.add(new FeePanel(loggedUser), "multas");
        mainPanel.add(new CollectionPanel(), "acervo");
        mainPanel.add(new AccountsPanel(), "contas");
    }
}