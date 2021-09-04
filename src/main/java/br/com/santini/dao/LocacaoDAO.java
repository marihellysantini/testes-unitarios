package br.com.santini.dao;

import java.util.List;

import br.com.santini.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
