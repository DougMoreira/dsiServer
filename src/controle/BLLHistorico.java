package controle;

import java.util.List;

import modelo.dao.DAOHistorico;
import modelo.entidade.Historico;

public class BLLHistorico implements IBLLCrud<Historico> {

	public int salvar(Historico entidade) {
		return new DAOHistorico().salvar(entidade);
	}

	public boolean excluir(Historico entidade) {
		return new DAOHistorico().excluir(entidade);
	}

	public List<Historico> listar() {
		return new DAOHistorico().listar();
	}

	public Historico buscarPorCodigo(int codigo) {
		return new DAOHistorico().buscarPorCodigo(codigo);
	}
}