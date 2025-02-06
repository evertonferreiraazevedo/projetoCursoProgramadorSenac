package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;

public class MainAppScreen {

    // Componentes da tela para manipular dados
    private JTextField nomeProdutoField;
    private JTextField precoProdutoField;
    private JTextField quantidadeProdutoField;
    private JTextArea resultadoConsultaArea;

    // Método que exibe a tela principal da aplicação
    /**
     * @wbp.parser.entryPoint
     */
    public void showScreen() {
        JFrame frame = new JFrame("Tela Principal");
        frame.setBounds(100, 100, 635, 500); // Posição e tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar a janela

        JPanel panel = new JPanel(); // Criação do painel principal
        panel.setBackground(new Color(144, 238, 144)); // Cor de fundo
        frame.getContentPane().add(panel); // Adiciona o painel à janela
        panel.setLayout(null); // Layout nulo para posicionamento manual

        // Rótulo de boas-vindas
        JLabel welcomeLabel = new JLabel("Bem-vindo à aplicação de gerenciamento de produtos!");
        welcomeLabel.setBounds(183, 25, 266, 25); // Posição e tamanho do rótulo
        panel.add(welcomeLabel);

        // Rótulo para o nome do produto
        JLabel nomeLabel = new JLabel("Nome do Produto:");
        nomeLabel.setBounds(155, 60, 150, 25);
        panel.add(nomeLabel);

        // Caixa de texto para o nome do produto
        nomeProdutoField = new JTextField();
        nomeProdutoField.setBounds(305, 60, 200, 25);
        panel.add(nomeProdutoField);

        // Rótulo para o preço do produto
        JLabel precoLabel = new JLabel("Preço do Produto:");
        precoLabel.setBounds(155, 100, 150, 25);
        panel.add(precoLabel);

        // Caixa de texto para o preço do produto
        precoProdutoField = new JTextField();
        precoProdutoField.setBounds(305, 100, 200, 25);
        panel.add(precoProdutoField);

        // Rótulo para a quantidade do produto
        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(155, 140, 150, 25);
        panel.add(quantidadeLabel);

        // Caixa de texto para a quantidade do produto
        quantidadeProdutoField = new JTextField();
        quantidadeProdutoField.setBounds(305, 140, 200, 25);
        panel.add(quantidadeProdutoField);

        // Botão para inserir produto
        JButton btnInsert = new JButton("Inserir Produto");
        btnInsert.setBounds(155, 180, 150, 40);
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeProdutoField.getText();
                    BigDecimal preco = new BigDecimal(precoProdutoField.getText());
                    int quantidade = Integer.parseInt(quantidadeProdutoField.getText());
                    Conectar.inserirDados(nome, preco, quantidade); // Chama a função para inserir dados no banco
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao inserir dados: " + ex.getMessage());
                }
            }
        });
        panel.add(btnInsert);

        // Botão para consultar produtos
        JButton btnConsult = new JButton("Consultar Produtos");
        btnConsult.setBounds(325, 180, 180, 40);
        btnConsult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    resultadoConsultaArea.setText(""); // Limpa a área de resultados antes de exibir os novos resultados
                    Conectar.consultarDados(resultadoConsultaArea); // Chama a função para consultar dados
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao consultar dados: " + ex.getMessage());
                }
            }
        });
        panel.add(btnConsult);

        // Área de resultados para consulta de produtos
        resultadoConsultaArea = new JTextArea();
        resultadoConsultaArea.setEditable(false);
        resultadoConsultaArea.setBounds(133, 230, 400, 100);
        panel.add(resultadoConsultaArea);

        // Botão de sair
        JButton btnLogout = new JButton("Sair");
        btnLogout.setBounds(229, 413, 200, 40);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fecha a aplicação
            }
        });
        panel.add(btnLogout);

        // Botão para atualizar preço do produto
        JButton btnUpdate = new JButton("Atualizar Preço");
        btnUpdate.setBounds(155, 363, 150, 40);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeProdutoField.getText();
                    BigDecimal preco = new BigDecimal(precoProdutoField.getText());
                    Conectar.atualizarDados(nome, preco); // Chama a função para atualizar o preço
                    resultadoConsultaArea.setText(""); // Limpa a área de resultados antes de exibir os novos resultados
                    Conectar.consultarDados(resultadoConsultaArea); // Chama a função para consultar dados
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao atualizar preço: " + ex.getMessage());
                }
            }
        });
        panel.add(btnUpdate);

        // Botão para deletar produto
        JButton btnDelete = new JButton("Deletar Produto");
        btnDelete.setBounds(325, 363, 180, 40);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeProdutoField.getText();
                    Conectar.deletarDados(nome); // Chama a função para deletar dados do banco
                    resultadoConsultaArea.setText(""); // Limpa a área de resultados antes de exibir os novos resultados
                    Conectar.consultarDados(resultadoConsultaArea); // Chama a função para consultar dados
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao deletar produto: " + ex.getMessage());
                }
                
            }
        });
        panel.add(btnDelete);

        frame.setVisible(true); // Torna a janela visível
    }
}
