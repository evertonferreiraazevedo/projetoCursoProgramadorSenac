package main;
import java.math.BigDecimal;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class conectar {

	static String URL = "jdbc:mysql://localhost:3306/senac";
	static String USER = "root";
	static String PASSWORD = "123456";

	public static Connection conexao_com_banco() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);		}
		catch (SQLException e){
			System.err.println("ERRO, QUEBROU TUDO :c " + e.getMessage());			
			return null;		}}

	public static void inserirDados() throws SQLException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o nome do produto:");
		String nome = scanner.nextLine();
		System.out.println("Digite o preço do produto:");
		BigDecimal preco = new BigDecimal(scanner.nextLine());
		System.out.println("Digite a quantidade do produto:");
		int quantidade = Integer.parseInt(scanner.nextLine());

		String sql = "INSERT INTO produtos(nome, preco, quantidade) values (?,?,?);";
		try(Connection realizar_conexao = conexao_com_banco();
				PreparedStatement  cursor = realizar_conexao.prepareStatement(sql)){

			cursor.setString(1, nome);	
			cursor.setBigDecimal(2, preco);	
			cursor.setInt(3, quantidade);	
			cursor.executeUpdate();
			System.out.println("Inserido com sucesso ");	}}

	public static void consultarDados() throws SQLException {
		String sql = "Select * from produtos";

		try(Connection realizar_conexao = conexao_com_banco();
				Statement cursor = realizar_conexao.createStatement();
				ResultSet resultado_consulta = cursor.executeQuery(sql)
				){

			while (resultado_consulta.next()) {
				int id = resultado_consulta.getInt("id");
				String nome = resultado_consulta.getString("nome");
				double preco = resultado_consulta.getDouble("preco");
				int quantidade = resultado_consulta.getInt("quantidade");

				System.out.printf("ID: %d, Nome: %s, Preço: %.2f, Quantidade: %d%n",
						id, nome, preco, quantidade);
			}

			System.out.println(resultado_consulta);
		}	}

	public static void atualizarDados() throws SQLException {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Digite o nome do produto que deseja atualizar:");
		String nome = scanner.nextLine();

		System.out.println("Digite o novo preço do produto:");
		BigDecimal preco = new BigDecimal(scanner.nextLine());

		String sql = "UPDATE produtos SET preco = ? WHERE nome = ?";

		try (Connection conexao = conexao_com_banco();
				PreparedStatement cursor = conexao.prepareStatement(sql)) {

			cursor.setBigDecimal(1, preco);
			cursor.setString(2, nome);
			int linhasAfetadas = cursor.executeUpdate();

			if (linhasAfetadas > 0) {
				System.out.println("Produto/Produtos atualizado com sucesso!");
			} else {
				System.out.println("Produto não encontrado.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deletarDados()	 throws SQLException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o nome do produto que deseja excluir:");
		String nome = scanner.nextLine();
		String sql = "DELETE FROM produtos WHERE nome = ?";

		try (Connection conexao = conexao_com_banco();
				PreparedStatement cursor = conexao.prepareStatement(sql)) {
			cursor.setString(1, nome);
			cursor.executeUpdate();
			System.out.println("Excluido com Sucesso!");
		}    }

	public static void main(String[] args) throws SQLException {

		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nEscolha uma opção: \n1. Inserir Dados \n2. Consultar Dados");
			System.out.println("3. Atualizar Dados \n4. Deletar Dados \n5. Sair");
			int opcao = Integer.parseInt(scanner.nextLine());


			switch(opcao) {
			case 1:
				inserirDados();
				break;
			case 2:
				consultarDados();
				break;
			case 3:
				atualizarDados();
				break;
			case 4:
				deletarDados();
				break;
			case 5:
				return;
			}
		}
	}
}