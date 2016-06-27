package modelo.dao;

import java.sql.*;
import java.util.*;
import modelo.conf.Conexao;
import modelo.entidade.Dispositivo;

public class DAODispositivo implements IDAOCrud<Dispositivo> {

	public int salvar(Dispositivo entidade) {

		Connection conexao = new Conexao().geraConexao();
		PreparedStatement sqlParametro = null;
		ResultSet resultado = null;
		int codigo = 0;
		String sql;
		try {
			sql = "insert into dispositivo(mac_dispositivo, pass_dispositivo) values(?, ?);";
			sqlParametro = conexao.prepareStatement(sql);
			sqlParametro.setString(1, entidade.getMac());
			sqlParametro.setInt(2, entidade.getPass());
			sqlParametro.executeUpdate();
			sql = "select last_insert_id() as cod_dispositivo";
			sqlParametro = conexao.prepareStatement(sql);
			resultado = sqlParametro.executeQuery();
			if (resultado.next()) {
				codigo = resultado.getInt("cod_dispositivo");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlParametro.close();
				conexao.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return codigo;
	}

	public boolean excluir(Dispositivo entidade) {

		Connection conexao = new Conexao().geraConexao();
		PreparedStatement sqlParametro = null;
		String sql;
		boolean teste = false;
		try {
			sql = "delete from dispositivo where cod_dispositivo = ?;";
			sqlParametro = conexao.prepareStatement(sql);
			sqlParametro.setInt(1, entidade.getCodigo());
			sqlParametro.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlParametro.close();
				conexao.close();
				teste = true;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return teste;
	}

	public List<Dispositivo> listar() {

		Connection conexao = new Conexao().geraConexao();
		List<Dispositivo> lista = new ArrayList<Dispositivo>();
		Statement consulta = null;
		ResultSet resultado = null;
		Dispositivo entidade = null;
		String sql;
		try {
			sql = "select * from dispositivo order by cod_dispositivo;";
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			while (resultado.next()) {
				entidade = new Dispositivo(resultado.getString("mac_dispositivo"), resultado.getInt("pass_dispositivo"));
				entidade.setCodigo(resultado.getInt("cod_dispositivo"));
				lista.add(entidade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return lista;
	}

	public Dispositivo buscarPorCodigo(int codigo) {

		Connection conexao = new Conexao().geraConexao();
		PreparedStatement consulta = null;
		ResultSet resultado = null;
		Dispositivo entidade = null;
		String sql;
		try {
			sql = "select * from dispositivo where cod_dispositivo = ?;";
			consulta = conexao.prepareStatement(sql);
			consulta.setInt(1, codigo);
			resultado = consulta.executeQuery();
			if (resultado.next()) {
				entidade = new Dispositivo(resultado.getString("mac_dispositivo"), resultado.getInt("pass_dispositivo"));
				entidade.setCodigo(resultado.getInt("cod_dispositivo"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return entidade;
	}

}