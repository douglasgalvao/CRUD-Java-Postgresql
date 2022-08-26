package exercicio2;

import java.sql.*;

public class DAO {
	private Connection conexao;

	public DAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirCarro(Carro carro) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate(
					"INSERT INTO \"exercicio2\".\"Carro\" (id, nome, modelo, ano) " + "VALUES (" + carro.getid() + ", '"
							+ carro.getnome() + "', '" + carro.getmodelo() + "', '" + carro.getano() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarCarro(Carro carro) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE \"exercicio2\".\"Carro\"  SET nome = '" + carro.getnome() + "', senha = '"
					+ carro.getmodelo() + "', sexo = '" + carro.getano() + "'" + " WHERE codigo = " + carro.getid();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

//	
	public boolean excluirCarro(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM \"exercicio2\".\"Carro\" WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

//	
	public Carro[] getCarros() {
		Carro[] carros = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM \"exercicio2\".\"Carro\"");
			if (rs.next()) {
				rs.last();
				carros = new Carro[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					carros[i] = new Carro(rs.getInt("id"), rs.getString("nome"), rs.getString("modelo"),
							rs.getInt("ano"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}

	public Carro[] getCarroPorModelo(String modelo) {

		Carro[] carros = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(
					"SELECT * FROM \"exercicio2\".\"Carro\" WHERE \"exercicio2\".\"Carro\".modelo LIKE " + modelo + "");
			if (rs.next()) {
				rs.last();
				carros = new Carro[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					carros[i] = new Carro(rs.getInt("id"), rs.getString("nome"), rs.getString("modelo"),
							rs.getInt("ano"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}

	public Carro[] getCarroPorAno(int ano) {
		Carro[] carros = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(
					"SELECT * FROM \"exercicio2\".\"Carro\" WHERE \"exercicio2\".\"Carro\".modelo LIKE " + ano + "");
			if (rs.next()) {
				rs.last();
				carros = new Carro[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					carros[i] = new Carro(rs.getInt("id"), rs.getString("nome"), rs.getString("modelo"),
							rs.getInt("ano"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}
}