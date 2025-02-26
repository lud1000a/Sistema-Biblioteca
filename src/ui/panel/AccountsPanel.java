package ui.panel;

import javax.swing.*;
import java.awt.*;

// Aqui será a parte responsável pelo painel que mostra todas as contas da biblioteca. Esse painel somente irá aparecer para administradores.
public class AccountsPanel extends JPanel{
    public AccountsPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Gerenciamento de Contas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.CENTER);

        // Mensagem genérica informativa.
        JLabel subtitle = new JLabel("Área para gerenciar usuários e permissões", SwingConstants.CENTER);
        add(subtitle, BorderLayout.SOUTH);
    }
}