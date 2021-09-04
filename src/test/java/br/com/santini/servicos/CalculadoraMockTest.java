package br.com.santini.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.santini.sesrvicos.Calculadora;


public class CalculadoraMockTest {

	@Test
	public void teste(){
		Calculadora calc = Mockito.mock(Calculadora.class);
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 100000));
	}
}
