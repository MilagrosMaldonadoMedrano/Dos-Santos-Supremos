package excepciones;

/*
 * El torneo no se puede iniciar: no tiene 4 u 8 parejas, o ya se jugó.
 * La lanza: Torneo.iniciar. La atrapan: los main (App, App2 y App3).
 */
public class TorneoInvalidoException extends TorneoException {
	private static final long serialVersionUID = 1L;

	/*
	 * Crea la excepción con el mensaje que explica por qué no se puede iniciar.
	 * Lo usa: Torneo.
	 */
	public TorneoInvalidoException(String mensaje) { super(mensaje); }
}
