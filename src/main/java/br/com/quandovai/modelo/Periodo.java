package br.com.quandovai.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum Periodo {

	DIARIO(d -> d.plus(1, ChronoUnit.DAYS)), SEMANAL(d -> d.plus(1, ChronoUnit.WEEKS)), QUINZENAL(
			d -> d.plus(15, ChronoUnit.DAYS)), MENSAL(d -> d.plus(1, ChronoUnit.MONTHS)), BIMESTRAL(
					d -> d.plus(2, ChronoUnit.MONTHS)), SEMESTRAL(
							d -> d.plus(6, ChronoUnit.MONTHS)), ANUAL(d -> d.plus(1, ChronoUnit.YEARS));

	private Ajuste ajuste;

	private Periodo(Ajuste ajuste) {
		this.ajuste = ajuste;
	}

	public Ajuste getAjuste() {
		return ajuste;
	}

	public interface Ajuste {
		LocalDateTime adiantar(LocalDateTime base);
	}
}
