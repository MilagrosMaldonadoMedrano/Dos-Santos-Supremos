package excepciones;

/*
 * Se rechazó una inscripción: la pareja ya estaba inscripta, uno de sus combatientes
 * ya está en otra pareja, o el torneo ya se jugó.
 * La lanza: Torneo.inscribir. La atrapa: el main (App).
 */
public class InscripcionInvalidaException extends TorneoException {
	private static final long serialVersionUID = 1L;

	/*
	 * Crea la excepción con el mensaje que explica por qué se rechazó la inscripción.
	 * Lo usa: Torneo.
	 */
	public InscripcionInvalidaException(String mensaje) { super(mensaje); }
}
