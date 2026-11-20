package interesado;

/*
 * OBSERVER: cualquier clase que quiera enterarse de lo que pasa en el torneo.
 * Tiene un método por cada tipo de acontecimiento. Son "default" (vacíos): cada
 * interesado redefine solo los que le importan. Así se puede sumar un interesado nuevo
 * sin modificar la lógica del torneo.
 * La implementan: HeraldoImperial y CronistaDeLaSecta.
 */
public interface Interesado {

	/*
	 * Se llama cuando empieza un combate.
	 * Lo llama: InicioCombate.notificarA.
	 */
	default void inicioCombate(InicioCombate acontecimiento) {}

	/*
	 * Se llama cuando un combatiente realiza una acción (ataque básico o técnica).
	 * Lo llama: AccionRealizada.notificarA.
	 */
	default void accionRealizada(AccionRealizada acontecimiento) {}

	/*
	 * Se llama cuando un combatiente queda fuera de combate.
	 * Lo llama: Eliminacion.notificarA.
	 */
	default void eliminacion(Eliminacion acontecimiento) {}

	/*
	 * Se llama cuando termina un combate.
	 * Lo llama: ResultadoCombate.notificarA.
	 */
	default void resultadoCombate(ResultadoCombate acontecimiento) {}

	/*
	 * Se llama cuando se proclama a la pareja campeona.
	 * Lo llama: Proclamacion.notificarA.
	 */
	default void proclamacion(Proclamacion acontecimiento) {}
}
