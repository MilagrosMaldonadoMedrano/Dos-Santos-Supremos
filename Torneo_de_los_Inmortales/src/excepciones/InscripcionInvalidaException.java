package excepciones;

// Se rechazó una inscripción: pareja repetida o combatiente ya inscripto.
public class InscripcionInvalidaException extends TorneoException {
	private static final long serialVersionUID = 1L;

	public InscripcionInvalidaException(String mensaje) { super(mensaje); }
}