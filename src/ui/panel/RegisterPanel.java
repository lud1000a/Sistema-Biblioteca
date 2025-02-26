package ui.panel;

import models.Student;
import exceptions.StudentAlreadyRegisteredException;
import ui.frame.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Essa tela é responsável pela a criação de um novo usuário.
public class RegisterPanel extends JPanel{
    private JTextField txtName;
    private JTextField txtId;
    private JPasswordField txtPassword;
//    private JRadioButton radioAdmin;
//    private JRadioButton radioStudent;
    private MainFrame mainFrame;

    // Referencia a janela principal.
    public RegisterPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        configLayout();
        addComponent();
    }

    // Usa o GridBagLayout.
    private void configLayout(){
        setLayout(new GridBagLayout());
    }

    // Neste trecho, um metodo para adicionar e organizar os componentes.
    private void addComponent(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Um grupo de seleção para o tipo de usuário.
        JPanel radioPanel = new JPanel(new GridLayout(1, 2, 10, 0));
//        radioAdmin = new JRadioButton("Administrador"); // Botão para administrador.
//        radioStudent = new JRadioButton("Estudante"); //Botão para estudante.
        ButtonGroup group = new ButtonGroup();
//        group.add(radioAdmin);
//        group.add(radioStudent);
//        radioPanel.add(radioAdmin);
//        radioPanel.add(radioStudent);

        // Campos de entrada.
        txtName = new JTextField(15);
        txtId = new JTextField(15);
        txtPassword = new JPasswordField(15);

        // Botões para finalizar o cadastro ou voltar.
        JButton finishButton = new JButton("Finalizar Cadastro");

        // Organização do layout de cima para baixo.
        gbc.gridy = 0; add(new JLabel("Novo Aluno"), gbc); // Label.
//        gbc.gridy = 1; add(radioPanel, gbc); // Adiciona os botões rádio. Como eles estão em um grupo serão adicionados na mesma linha.
        gbc.gridy = 1; add(new JLabel("Nome:"), gbc); // Nome do novo aluno.
        gbc.gridy = 2; add(txtName, gbc); // Campo de entrada do nome do usuário.
        gbc.gridy = 3; add(new JLabel("Matricula:"), gbc);
        gbc.gridy = 4; add(txtId, gbc);
        gbc.gridy = 5; add(new JLabel("Senha:"), gbc); // Senha do novo aluno.
        gbc.gridy = 6; add(txtPassword, gbc); // Campo de entrada para a senh.
        gbc.gridy = 7; add(finishButton, gbc); // Adiciona o botão finalizar.

        // Configura a ação do botão "Finalizar".
        finishButton.addActionListener(e -> registerUser());
    }

    //TODO Método responsável por realizar o cadastro.
    private void registerUser(){
    	String id = txtId.getText().trim();
    	String name = txtName.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
    	try {
    		Student.register(id, name, password);
    		JOptionPane.showMessageDialog(this, "ESTUDANTE CRIADO COM SUCESSO!!", "Aviso", JOptionPane.INFORMATION_MESSAGE); //Exibe mensagem de estudante criado com sucesso
    	}
    	catch(StudentAlreadyRegisteredException sare) {
    		JOptionPane.showMessageDialog(this, sare.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    	}
    	catch(IOException ioe) {
    		JOptionPane.showMessageDialog(this, ioe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    	}
    }
}