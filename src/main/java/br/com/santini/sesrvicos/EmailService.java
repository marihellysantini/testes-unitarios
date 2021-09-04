package br.com.santini.sesrvicos;

import br.com.santini.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);

}
