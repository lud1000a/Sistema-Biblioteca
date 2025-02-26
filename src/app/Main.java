package app;
import ui.frame.MainFrame;
import models.Student;

public class Main{
    public static void main(String[] args){
        // Garante que a interface gráfica seja criada e atualizada de forma segura.
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame janela = new MainFrame();  // Instância a janela principal.
            janela.setVisible(true);
        });
    }
}