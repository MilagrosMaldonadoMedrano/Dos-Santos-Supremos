package equipamiento_y_tecnicas;

import combatiente.Combatiente;

/*
 * Clase abstracta de todas las piezas de equipo. Una pieza tiene nombre y categoría,
 * y sabe aplicar y quitar su propio efecto sobre el combatiente (polimorfismo): el
 * combatiente nunca pregunta qué pieza es.
 * Subclases: Arma, Armadura, Anillo y Sello.
 */
public abstract class Equipamiento {
	private final String nombre;

	/*
	 * Guarda el nombre de la pieza. Si es null o está vacío, lanza IllegalArgumentException.
	 * Es protected porque solo lo llaman las subclases con super(nombre).
	 */
	protected Equipamiento(String nombre) {
		if (nombre == null || nombre.isBlank())
			throw new IllegalArgumentException("El equipamiento necesita nombre");
		this.nombre = nombre;
	}

	/*
	 * Devuelve el nombre de la pieza (por ejemplo, "Espada celestial").
	 * Lo usan: Combatiente.desequipar (mensaje de error) y el toString de cada pieza.
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * POLIMORFISMO: cada subclase dice a qué categoría pertenece (ARMA, ARMADURA, ANILLO
	 * o SELLO). Es abstracto: Equipamiento no la sabe, la define cada subclase.
	 * Lo usa: Combatiente.equipar, para rechazar dos piezas de la misma categoría.
	 */
	public abstract Categoria getCategoria();

	/*
	 * POLIMORFISMO: aplica el efecto de la pieza sobre el combatiente (suma daño, armadura,
	 * energía o vida máxima, según la pieza). Abstracto: lo define cada subclase.
	 * Lo usa: Combatiente.equipar, después de validar las reglas.
	 */
	public abstract void aplicarEfecto(Combatiente c);

	/*
	 * POLIMORFISMO: deshace el efecto de la pieza, restando lo mismo que sumó, así el
	 * combatiente vuelve a como estaba. Abstracto: lo define cada subclase.
	 * Lo usa: Combatiente.desequipar.
	 */
	public abstract void quitarEfecto(Combatiente c);
}
