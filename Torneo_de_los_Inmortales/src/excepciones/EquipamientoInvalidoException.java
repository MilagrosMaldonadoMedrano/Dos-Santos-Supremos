package excepciones;

// Se intentó romper una regla del equipamiento (§4): cuarta pieza, categoría
// repetida, cambiar el equipo en combate o quitar una pieza que no tiene.
public class EquipamientoInvalidoException extends TorneoException {
	private static final long serialVersionUID = 1L;
	public EquipamientoInvalidoException(String mensaje) { super(mensaje); }
}