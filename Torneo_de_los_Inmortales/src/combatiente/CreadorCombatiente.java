package combatiente;
import escuela.Escuela;

import excepciones.EquipamientoInvalidoException;

/*
 * FACTORY: centraliza la creación de combatientes. Quien pide un combatiente solo dice
 * el tipo (con una de las constantes de abajo), el nombre y la escuela; nunca conoce la
 * clase concreta ni cómo se arma su preparación. Asigna los identificadores en orden
 * de creación.
 * Lo usan: los main (App, App2 y App3) y las pruebas que necesitan combatientes preparados.
 */
public class CreadorCombatiente {
	public static final String CULTIVADOR = "Cultivador de la Espada";
	public static final String MONJE = "Monje del Cuerpo Celestial";
	public static final String HECHICERO = "Hechicero del Vacío";

	private int proximoId = 1;   // el próximo identificador a asignar

	/*
	 * Crea un combatiente del tipo pedido, le asigna el siguiente identificador y le pide
	 * a la escuela que lo prepare (le enseña las técnicas y le equipa las piezas).
	 * Devuelve el combatiente como Combatiente, sin mostrar su clase concreta.
	 * Si la escuela no puede equiparlo, lanza EquipamientoInvalidoException.
	 * Lo usan: los main (App, App2 y App3) y las pruebas.
	 */
	public Combatiente crear(String tipo, String nombre, Escuela escuela)throws EquipamientoInvalidoException {
		Combatiente nuevo = instanciar(tipo, proximoId, nombre);
		proximoId++;
		escuela.preparar(nuevo);
		return nuevo;
	}

	/*
	 * Traduce el texto del tipo en la clase concreta (el único lugar del sistema que la
	 * conoce). Si el tipo es null o desconocido, lanza IllegalArgumentException.
	 * Para agregar un tipo nuevo alcanza con sumar una línea en el switch.
	 * Lo usa: crear.
	 */
	private Combatiente instanciar(String tipo, int id, String nombre) {
		if (tipo == null) {
			throw new IllegalArgumentException("El tipo no puede ser null.");
		}
		return switch (tipo) {
			case CULTIVADOR -> new CultivadorDeLaEspada(id, nombre);
			case MONJE -> new MonjeDelCuerpoCelestial(id, nombre);
			case HECHICERO -> new HechiceroDelVacio(id, nombre);
			default -> throw new IllegalArgumentException("Tipo de combatiente desconocido: " + tipo);
		};
	}
}
