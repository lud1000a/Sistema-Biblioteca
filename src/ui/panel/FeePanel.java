package ui.panel;

import javax.swing.*;
import java.awt.*;
import models.Student;
// Tela dedicada para possíveis fees que o usuário possa ter
public class FeePanel extends JPanel{
    private boolean fees = false;
    private Student user; //TODO adaptar para administradores

    public FeePanel(Student user){
        this.user = user;
        configPanel();
    }

    private void configPanel(){
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Multas Pendentes:", SwingConstants.CENTER); // Define a mensagem.
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.CENTER); // Centraliza a mensagem.

        //COMENTARIO PARA A EQUIPE
        // Necessario que seja puxado do banco de dados uma possível multa. Se não houver multa, a mensagem abaixo não deve ser exibida.
        if(fees){
            JLabel oldFee = new JLabel("Multas Pendentes: \n" + "NOMEDOLIVROAQUI: VALORDAMULTA", SwingConstants.CENTER);
            add(oldFee, BorderLayout.SOUTH);
        }

        // Se o usuário for uma adiministrador ele verá todas as fees de todos os usuários cadastrados.
        if(user != null && user.isAdmin()){
            // Essa parte do código precisa que seja integrada com o código de pesquisa de multa de usuários feito pela Miriam

            JLabel subtitle = new JLabel("Área para visualizar as fees de todas as contas.", SwingConstants.CENTER);
            add(subtitle, BorderLayout.SOUTH);
        }else{
            JLabel subtitle = new JLabel("Em caso de multa. A multa deve ser paga presencialmente na recepção.", SwingConstants.CENTER);
            add(subtitle, BorderLayout.SOUTH);
        }
    }
}