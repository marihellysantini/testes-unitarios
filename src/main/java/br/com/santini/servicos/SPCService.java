package br.com.santini.servicos;

import br.com.santini.entidades.Usuario;

public interface SPCService {

	public boolean possuiNegativacao(Usuario usuario) throws Exception;
}
