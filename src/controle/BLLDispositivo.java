package controle;

import java.util.List;

import modelo.dao.DAODispositivo;
import modelo.entidade.Dispositivo;

public class BLLDispositivo implements IBLLCrud<Dispositivo> {

	public int salvar(Dispositivo entidade) {
		return new DAODispositivo().salvar(entidade);
	}

	public boolean excluir(Dispositivo entidade) {
		return new DAODispositivo().excluir(entidade);
	}

	public List<Dispositivo> listar() {
		return new DAODispositivo().listar();
	}
	
	public void update(Dispositivo entidade) {
		new DAODispositivo().update(entidade);
	}

	public Dispositivo buscarPorCodigo(int codigo) {
		return new DAODispositivo().buscarPorCodigo(codigo);
	}
}