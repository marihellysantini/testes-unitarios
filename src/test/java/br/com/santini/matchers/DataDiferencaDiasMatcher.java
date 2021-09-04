package br.com.santini.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.santini.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

	private Integer qtdDias;
	
	public DataDiferencaDiasMatcher(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}
	
	public void describeTo(Description arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
	}

}
