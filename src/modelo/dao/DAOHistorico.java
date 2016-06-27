package modelo.dao;

import java.sql.*;
import java.util.*;
import modelo.conf.Conexao;
import modelo.entidade.Historico;

public class DAOHistorico implements IDAOCrud<Historico> {

	public int salvar(Historico entidade) {

		Connection conexao = new Conexao().geraConexao();
		PreparedStatement sqlParametro = null;
		ResultSet resultado = null;
		int codigo = 0;
		String sql;
		try {
			sql = "insert into historico(cod_dispositivo, parametro, data_comando) values(?, ?, ?);";
			sqlParametro = conexao.prepareStatement(sql);
			sqlParametro.setInt(1, entidade.getCodigoDispositivo());
			sqlParametro.setString(2, entidade.getParametro());
			sqlParametro.setDate(3, entidade.getDataComando());
			sqlParametro.executeUpdate();
			sql = "select last_insert_id() as cod_historico";
			sqlParametro = conexao.prepareStatement(sql);
			resultado = sqlParametro.executeQuery();
			if (resultado.next()) {
				codigo = resultado.getInt("cod_historico");
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

	public boolean excluir(Historico entidade) {

		Connection conexao = new Conexao().geraConexao();
		PreparedStatement sqlParametro = null;
		String sql;
		boolean teste = false;
		try {
			sql = "delete from historico";
			sqlParametro = conexao.prepareStatement(sql);
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

	public List<Historico> listar() {

		Connection conexao = new Conexao().geraConexao();
		List<Historico> lista = new ArrayList<Historico>();
		Statement consulta = null;
		ResultSet resultado = null;
		Historico entidade = null;
		String sql;
		try {
			sql = "select * from historico order by cod_historico;";
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			while (resultado.next()) {
				entidade = new Historico();
				entidade.setCodigo(resultado.getInt("cod_historico"));
				entidade.setCodigoDispositivo(resultado.getInt("cod_dispositivo"));
				entidade.setDataComando(resultado.getDate("data_comando"));
				entidade.setParametro(resultado.getString("parametro"));
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

	public Historico buscarPorCodigo(int codigo) {

		Connection conexao = new Conexao().geraConexao();
		PreparedStatement consulta = null;
		ResultSet resultado = null;
		Historico entidade = null;
		String sql;
		try {
			sql = "select * from historico where cod_historico = ?;";
			consulta = conexao.prepareStatement(sql);
			consulta.setInt(1, codigo);
			resultado = consulta.executeQuery();
			if (resultado.next()) {
				entidade = new Historico();
				entidade.setCodigo(resultado.getInt("cod_historico"));
				entidade.setCodigoDispositivo(resultado.getInt("cod_dispositivo"));
				entidade.setDataComando(resultado.getDate("data_comando"));
				entidade.setParametro(resultado.getString("parametro"));

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