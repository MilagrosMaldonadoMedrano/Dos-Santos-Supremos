package excepciones;

// Se intentó usar una técnica que no se puede ejecutar (§6): no la conoce,
// sin usos, sin energía, o el combatiente o el objetivo no están en pie.
public class TecnicaNoDisponibleException extends TorneoException {
	private static final long serialVersionUID = 1L;
	public TecnicaNoDisponibleException(String mensaje) { super(mensaje); }
}