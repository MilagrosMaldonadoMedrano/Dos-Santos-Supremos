package excepciones;

/*
 * Se intentó usar una técnica que no se puede ejecutar: no la conoce, no le quedan usos,
 * no le alcanza la energía, o el combatiente o el objetivo no están en pie.
 * La lanzan: Combatiente.usarTecnica y Tecnica.realizar. La atrapa: Combate.actuar,
 * que en ese caso hace un ataque básico.
 */
public class TecnicaNoDisponibleException extends TorneoException {
	private static final long serialVersionUID = 1L;

	/*
	 * Crea la excepción con el mensaje que explica por qué no se pudo usar la técnica.
	 * Lo usan: Combatiente y Tecnica.
	 */
	public TecnicaNoDisponibleException(String mensaje) { super(mensaje); }
}
