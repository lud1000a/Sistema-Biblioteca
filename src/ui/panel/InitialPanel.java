package ui.panel;

import javax.swing.*;
import java.awt.*;

// A primeira tela que o usário vê ao abrir a janela.
public class InitialPanel extends JPanel{
    public InitialPanel() {
        setLayout(new GridBagLayout());
        add(new JLabel("Bem-vindo à Biblioteca UFC")); // Exibe a mensagem
    }
}