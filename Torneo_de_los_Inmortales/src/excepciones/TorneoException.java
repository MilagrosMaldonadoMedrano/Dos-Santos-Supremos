package excepciones;

/*
 * Excepción base de todas las reglas del juego. Es verificada (hereda de Exception):
 * quien llama a un método que la lanza está obligado a atraparla o declararla.
 * Permite atrapar cualquier error del juego con un solo catch.
 * Subclases: EquipamientoInvalidoException, TecnicaNoDisponibleException,
 * InscripcionInvalidaException y TorneoInvalidoException.
 */
public class TorneoException extends Exception {
	private static final long serialVersionUID = 1L;

	/*
	 * Crea la excepción con el mensaje que explica qué regla se rompió.
	 * Lo usan: los constructores de las cuatro subclases (con super).
	 */
	public TorneoException(String mensaje) { super(mensaje); }
}
