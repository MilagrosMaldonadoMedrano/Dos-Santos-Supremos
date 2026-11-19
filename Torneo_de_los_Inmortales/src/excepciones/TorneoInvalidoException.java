package excepciones;

// El torneo no se puede iniciar (§5): no tiene 4 u 8 parejas.
public class TorneoInvalidoException extends TorneoException {
	private static final long serialVersionUID = 1L;
	public TorneoInvalidoException(String mensaje) { super(mensaje); }
}