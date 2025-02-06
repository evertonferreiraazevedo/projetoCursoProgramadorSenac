package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen {

    private JFrame frame;                // Declaração da janela principal
    private JTextField nameField;        // Campo de texto para o nome de usuário
    private JPasswordField passwordField; // Campo de texto para a senha

    // Método principal que inicializa a aplicação
    public static void main(String[] args) {
        // Chama a interface gráfica na thread de eventos
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginScreen window = new LoginScreen(); // Cria a tela de login
                    window.frame.setVisible(true); // Torna a janela visível
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Construtor que inicializa os componentes da tela de login
    public LoginScreen() {
        initialize(); // Chama o método para inicializar a interface
    }

    // Método que inicializa os componentes da interface
    private void initialize() {
        frame = new JFrame("Tela de Login"); // Cria a janela com título
        frame.setBounds(100, 100, 450, 350); // Define posição e tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela

        JPanel panel = new JPanel(); // Criação do painel principal
        panel.setBackground(new Color(173, 216, 230));  // Define a cor de fundo suave
        frame.getContentPane().add(panel); // Adiciona o painel à janela
        panel.setLayout(null); // Define o layout para posicionamento manual

        JLabel titleLabel = new JLabel("Bem-vindo! Faça seu login.");
        titleLabel.setBounds(120, 10, 200, 25); // Posição e tamanho do título
        panel.add(titleLabel); // Adiciona o título ao painel

        // Rótulo e campo de texto para nome de usuário
        JLabel nameLabel = new JLabel("Usuário:");
        nameLabel.setBounds(130, 40, 150, 25); // Posição e tamanho do rótulo
        panel.add(nameLabel); // Adiciona o rótulo ao painel

        nameField = new JTextField(); // Campo de texto para nome de usuário
        nameField.setBounds(130, 70, 150, 25); // Posição e tamanho do campo
        panel.add(nameField); // Adiciona o campo ao painel

        // Rótulo e campo de senha
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(130, 100, 150, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(); // Campo de senha
        passwordField.setBounds(130, 130, 150, 25);
        panel.add(passwordField);

        // Botão de login com ação
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(146, 170, 131, 56);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText(); // Obtém nome do usuário
                String password = new String(passwordField.getPassword()); // Obtém senha

                // Verifica se o nome e a senha são corretos
                if (name.equals("admin") && password.equals("1234")) {
                    openMainApp(); // Abre a tela principal
                    frame.setVisible(false); // Fecha a tela de login
                } else {
                    JOptionPane.showMessageDialog(frame, "Nome de usuário ou senha incorretos."); // Exibe erro
                }
            }
        });
        panel.add(btnLogin);

        // Botão de sair com ação para fechar a aplicação
        JButton btnExit = new JButton("Sair");
        btnExit.setBounds(146, 240, 131, 56);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fecha a aplicação
            }
        });
        panel.add(btnExit);
    }

    // Método para abrir a tela principal após o login bem-sucedido
    private void openMainApp() {
        // Cria a nova tela principal da aplicação
        MainAppScreen mainScreen = new MainAppScreen(); 
        mainScreen.showScreen(); // Exibe a tela principal
    }
}
