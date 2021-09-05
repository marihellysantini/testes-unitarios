package br.com.santini.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.santini.sesrvicos.Calculadora;

public class CalculadoraMockTest {

	@Mock
	private Calculadora calculadoraMock;

	@Spy
	private Calculadora calculadoraSpy;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveMostrarDiferencaEntreMockAndSpy() {
		Mockito.when(calculadoraMock.somar(1, 2)).thenReturn(8);
		// Mockito.when(calculadoraSpy.somar(1, 2)).thenReturn(8);

		// Ordem inversa para que o java n�o execute o m�todo havendo uma espectativa
		// gerada:
		Mockito.doReturn(8).when(calculadoraSpy.somar(1, 2));

		// Caso desejar que o spy n�o chame o m�todo real em alguma situa��o espec�fica:
		Mockito.doNothing().when(calculadoraSpy).imprime();

		// O padr�o do mock � que se n�o houver expectativa retornar� zero.
		System.out.println(calculadoraMock.somar(1, 3));

		// J� o spy, se n�o tiver expectativa ir� chamar o m�todo real e retornar� o
		// valor calculado.
		System.out.println(calculadoraSpy.somar(1, 3));

		// Caso desejar for�ar que o mock chame o m�todo, podemos usar o seguinte:
		Mockito.when(calculadoraMock.somar(1, 2)).thenCallRealMethod();

		// M�todos void
		calculadoraMock.imprime(); // n�o imprime nada
		calculadoraSpy.imprime(); // spy ir� executar o m�todo

	}

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5);

		Assert.assertEquals(5, calc.somar(1, 100000));
	}

	@Test
	public void testeComArgmentCaptor() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);

		Assert.assertEquals(5, calc.somar(1, 100000));
		// System.out.println(argCapt.getAllValues());
	}

}
