package ui.panel;

import models.Student;
import ui.frame.MainFrame;
import exceptions.StudentNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Uma tela dedicada ao login.
// Valida as credenciais e permite o acesso a tela de cadastro.
public class LoginPanel extends JPanel{
    private JTextField idField;
    private JPasswordField passwordField;
    private MainFrame mainFrame;

    // Referencia a janela principal para o controle de navegação.
    public LoginPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        configLayout();
        addComponents();
    }

    // Neste trecho, configura o layout principal com GridBagLayout.
    private void configLayout(){
        setLayout(new GridBagLayout());
    }

    // Faz a adição e a organização dos componentes.
    private void addComponents(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margens internas
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expansão horizontal.
        gbc.anchor = GridBagConstraints.CENTER; // Centralização vertical.

        // Configura os campos de entrada do usuário e da senha.
        idField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // Aqui são criados botões para entrar com um usuário já existente ou criar um novo.
        JButton loginButton = new JButton("Entrar");

        // Define a ordem das informações de cima para baixo.
        gbc.gridy = 0; add(new JLabel("Insira seu Login"), gbc); // Mensagem de login.
        gbc.gridy = 1; add(new JLabel("Matrícula:"), gbc); // Matricula.
        gbc.gridy = 2; add(idField, gbc); // Campo de entrada para a matrícula.
        gbc.gridy = 3; add(new JLabel("Senha:"), gbc); // Senha.
        gbc.gridy = 4; add(passwordField, gbc); // Campo de entrada para a senha.
        gbc.gridy = 5; add(loginButton, gbc); // Botão de login.

        // Configura a ação do botão.
        loginButton.addActionListener(e -> validarLogin());
    }

    //  Neste trecho, será feito a validação das informações inseridas pelo usuário.
    private void validarLogin(){
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        try {
        	Student student = Student.login(id, password); // Tipo será determinado depois //Fazer autenticacao tambem para adm TODO
        	//Caso o aluno nao seja encontrado, o codigo nao vai continuar a partir daqui
        	
        	mainFrame.setLoggedUser(student);
        	mainFrame.changePanel("logado");
        	mainFrame.getBarMenu().updateToLoggedUser(student);
        }
        catch(StudentNotFoundException snfe) {
        	JOptionPane.showMessageDialog(this, snfe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException ioe) {
        	JOptionPane.showMessageDialog(this, ioe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}