package excepciones;

/*
 * Se intentó romper una regla del equipamiento: una cuarta pieza, una categoría
 * repetida, cambiar el equipo durante el combate o quitar una pieza que no tiene.
 * La lanzan: Combatiente.equipar y desequipar. La propagan: Escuela.preparar y
 * CreadorCombatiente.crear. La atrapa: el main (App).
 */
public class EquipamientoInvalidoException extends TorneoException {
	private static final long serialVersionUID = 1L;

	/*
	 * Crea la excepción con el mensaje que explica qué regla se rompió.
	 * Lo usa: Combatiente.
	 */
	public EquipamientoInvalidoException(String mensaje) { super(mensaje); }
}
