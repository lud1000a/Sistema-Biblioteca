package ui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import ui.frame.MainFrame;
import models.Student;
// A barra de navegação lateral terá 2 estados (logado e não logado).
// Sua função principal é proporcionar a navegação entre as telas.
public class BarMenu extends JPanel{
    private MainFrame mainFrame; // Referencia a janela principal.

    public BarMenu(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        configNotLoggedMenu(); // O construtor irá iniciar com o estado de "não logado".
    }

    // Menu para usuários não logados
    private void configNotLoggedMenu(){
        removeComponents();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(150, 500));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        JButton btnLogin = createButton("Login", e -> mainFrame.changePanel("login"));
        addComponent(btnLogin, 20);
        refresh();
    }

    public void updateToLoggedUser(Student user){ //Adaptar para estudante e adm TODO
        removeComponents();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel userLabel = criarLabel("Usuário:");
        JLabel nameLabel = criarLabel(user.getName());
        JLabel typeLabel = criarLabel(user.isAdmin() ? "Administrador" : "Estudante");
        
        JButton searchButton = createButton("Pesquisa", e -> mainFrame.changePanel("logado"));
        JButton rentedButton = createButton("Histórico", e -> mainFrame.changePanel("alugueis"));
        JButton feesButton = createButton("Multas", e -> mainFrame.changePanel("multas"));
        JButton exitButton = createButton("Sair", e -> {
            mainFrame.changePanel("inicio");
            mainFrame.updateMenuBar(new BarMenu(mainFrame));
        });

        addComponent(userLabel, 20);
        addComponent(nameLabel, 10);
        addComponent(typeLabel, 10);
        addComponent(rentedButton, 30);
        addComponent(searchButton, 10);
        addComponent(feesButton, 10);

        // Caso o usuário seja um administrador, é preciso que sua barra de menu lateral possua botões específicos.
        if(user.isAdmin()){
            // Cria esses botões e suas respectivas ações.
            JButton registerButton = createButton("Criar Cadastro", e -> mainFrame.changePanel("cadastro"));
            JButton collectionButton = createButton("Acervo", e -> mainFrame.changePanel("acervo"));
            JButton accountsButton = createButton("Contas", e -> mainFrame.changePanel("contas"));

            // Adiciona os botões.
            addComponent(accountsButton, 10);
            addComponent(collectionButton, 10);
            addComponent(registerButton, 10);
        }

        addComponent(exitButton, 30);

        refresh();
    }

    // Métodos auxiliares para a criação dos componentes.
    private JButton createButton(String text, ActionListener action){
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinha à esquerda
        button.setMaximumSize(new Dimension(140, 30)); // Tamanho fixo.
        button.setFocusPainted(false); // Remove o efeito de foco.
        button.addActionListener(action);
        return button;
    }

    private JLabel criarLabel(String text){
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhamento à esquerda.
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Margem esquerda.
        return label;
    }

    private void addComponent(Component comp, int distance){
        add(Box.createVerticalStrut(distance)); // Espaçamento vertical.
        add(comp);
    }

    // Remove todos os componentes.
    private void removeComponents(){
        removeAll();
    }

    private void refresh(){
        revalidate(); // Atualiza o layout.
        repaint(); // Redesenha os componentes.
    }
}