package excepciones;

// Base de todas las excepciones del torneo (verificada, porque hereda de Exception).
// Permite atrapar cualquier error del juego con un solo catch si se quiere.
public class TorneoException extends Exception {
	private static final long serialVersionUID = 1L;
	public TorneoException(String mensaje) { super(mensaje); }
}