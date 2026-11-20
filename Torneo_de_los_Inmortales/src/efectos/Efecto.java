package efectos;

import combatiente.Combatiente;

/*
 * COMPOSITE: interfaz común de todos los efectos de las técnicas. Los efectos simples
 * (Daño, Curacion, DrenarEnergia, Reduccion, AplicarEfectoTemporal) y el compuesto
 * (EfectoCompuesto) se usan exactamente igual, con aplicarEfecto.
 */
public interface Efecto {

	/*
	 * Aplica el efecto. Recibe al ejecutor (quien usa la técnica) y al afectado.
	 * El ejecutor se pasa para que el daño use su multiplicador: así una misma técnica
	 * daña distinto según quién la usa, sin preguntar su tipo.
	 * Lo usan: Tecnica.realizar y EfectoCompuesto (para cada efecto que contiene).
	 */
	void aplicarEfecto(Combatiente ejecutor, Combatiente afectado);
}
