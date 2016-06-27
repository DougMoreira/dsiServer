package controle;

import java.util.List;

public interface IBLLCrud<Entity> {
		
	public abstract int salvar(Entity objeto);
	
	public abstract boolean excluir(Entity objeto);
	
	public abstract List<Entity> listar();
	
	public abstract Entity buscarPorCodigo(int codigo);
}
