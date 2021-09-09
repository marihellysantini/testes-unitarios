package br.com.santini.servicos;

import static br.com.santini.builders.FilmeBuilder.umFilme;
import static br.com.santini.builders.UsuarioBuilder.umUsuario;
import static br.com.santini.matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.com.santini.dao.LocacaoDAO;
import br.com.santini.entidades.Filme;
import br.com.santini.entidades.Locacao;
import br.com.santini.entidades.Usuario;
import br.com.santini.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocacaoService.class })
public class LocacaoServiceTest_PowerMock {

	@InjectMocks
	private LocacaoService service;

	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService email;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none(); 

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = PowerMockito.spy(service);
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));

		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 04, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 04, 2017)), is(true));
	}

	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));

		// acao
		Locacao retorno = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());

		// verificando a quantidade de vezes que a classe Date foi invocada.
		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
	}
	
	/**
	 * Exemplo de uso com m�todos est�ticos. Anotado como @ignore pois est� sendo utilizado a classe java.util.Date
	 * ao inv�s de Calendar. Mantido apenas para fins de exemplo de uso. 
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado_() throws Exception {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		// Exemplo de uso com m�todos est�ticos
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 29);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2017);

		PowerMockito.mockStatic(Calendar.class); // necess�rio!
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

		// acao
		Locacao retorno = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());

		PowerMockito.verifyStatic(Mockito.times(2));
		Calendar.getInstance();
	}


	@Test
	public void deveAlugarFilmeSemCalcularValor() throws Exception {
		// cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

		// a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verifica��o
		assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		// Cen�rio
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		//A��o
		Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
		
		//Verifica��o
		Assert.assertThat(valor, is(4.0));
	}
}
