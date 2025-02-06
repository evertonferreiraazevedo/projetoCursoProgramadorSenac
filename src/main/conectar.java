package main;

import java.sql.*;
import javax.swing.*;
import java.math.BigDecimal;

public class Conectar {

    private static final String URL = "jdbc:mysql://localhost:3306/senac"; // URL do banco de dados
    private static final String USER = "root"; // Usuário do banco de dados
    private static final String PASSWORD = "123456"; // Senha do banco de dados

    private static Connection conexao;

    // Método para conectar ao banco de dados
    public static void conectarBanco() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL, USER, PASSWORD); // Cria a conexão com o banco
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para inserir dados no banco de dados
    public static void inserirDados(String nome, BigDecimal preco, int quantidade) throws SQLException {
        conectarBanco(); // Garante que a conexão está ativa

        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, nome);
            pst.setBigDecimal(2, preco);
            pst.setInt(3, quantidade);
            pst.executeUpdate(); // Executa a inserção
            JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir produto: " + e.getMessage());
        }
    }

    // Método para consultar todos os dados do banco e exibir no JTextArea
    public static void consultarDados(JTextArea resultadoConsultaArea) throws SQLException {
        conectarBanco(); // Garante que a conexão está ativa

        String sql = "SELECT * FROM produtos"; // Consulta todos os produtos
        try (Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Nome: ").append(rs.getString("nome"))
                  .append(", Preço: ").append(rs.getBigDecimal("preco"))
                  .append(", Quantidade: ").append(rs.getInt("quantidade"))
                  .append("\n");
            }
            resultadoConsultaArea.setText(sb.toString()); // Exibe os resultados na área de texto
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar dados: " + e.getMessage());
        }
    }

    // Método para atualizar o preço de um produto
    public static void atualizarDados(String nome, BigDecimal preco) throws SQLException {
        conectarBanco(); // Garante que a conexão está ativa

        String sql = "UPDATE produtos SET preco = ? WHERE nome = ?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setBigDecimal(1, preco);
            pst.setString(2, nome);
            int linhasAfetadas = pst.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Preço do produto atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado para atualização!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar preço: " + e.getMessage());
        }
    }

    // Método para deletar um produto pelo nome
    public static void deletarDados(String nome) throws SQLException {
        conectarBanco(); // Garante que a conexão está ativa

        String sql = "DELETE FROM produtos WHERE nome = ?";
        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setString(1, nome);
            int linhasAfetadas = pst.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado para deleção!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar produto: " + e.getMessage());
        }
    }
}
