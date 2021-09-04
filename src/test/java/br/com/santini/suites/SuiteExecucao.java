package br.com.santini.suites;

import org.junit.runners.Suite.SuiteClasses;

import br.com.santini.servicos.CalculadoraTest;
import br.com.santini.servicos.CalculoValorLocacaoTest;
import br.com.santini.servicos.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({ CalculadoraTest.class, CalculoValorLocacaoTest.class, LocacaoServiceTest.class })
public class SuiteExecucao {
	// Remova se puder!
}
