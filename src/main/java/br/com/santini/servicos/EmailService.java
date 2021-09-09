package br.com.santini.servicos;

import br.com.santini.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);

}
