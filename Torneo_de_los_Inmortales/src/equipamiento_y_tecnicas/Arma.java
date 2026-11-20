package equipamiento_y_tecnicas;

import combatiente.Combatiente;

/*
 * Pieza de categoría ARMA: aumenta el daño del ataque básico mientras se lleva.
 * Subclase concreta: EspadaCelestial.
 */
public class Arma extends Equipamiento{

	private int dañoExtra;   // daño que suma al ataque básico

	/*
	 * Crea un arma con su nombre y el daño que suma. El daño tiene que ser positivo;
	 * si no, lanza IllegalArgumentException.
	 * Lo usan: EspadaCelestial (con super) y las pruebas que crean armas propias.
	 */
	public Arma(String nombre, int dañoExtra) {
		super(nombre);
		if (dañoExtra <= 0) throw new IllegalArgumentException("El daño extra debe ser positivo");
		this.dañoExtra = dañoExtra;
	}

	/*
	 * Devuelve cuánto daño suma el arma.
	 * Lo usa: toString, para mostrar la pieza.
	 */
	public int getDañoExtra() {
		return dañoExtra;
	}

	/*
	 * Indica que esta pieza es un ARMA.
	 * Lo usa: Combatiente.equipar, para no permitir dos armas.
	 */
	@Override
	public Categoria getCategoria() {
		return Categoria.ARMA;
	}

	/*
	 * Al equiparla, suma su daño al ataque básico del combatiente con modificarDanio.
	 * Lo usa: Combatiente.equipar.
	 */
	@Override
	public void aplicarEfecto(Combatiente c) {
		c.modificarDanio(dañoExtra);
	}

	/*
	 * Al quitarla, resta el mismo daño que sumó: el combatiente vuelve a como estaba.
	 * Lo usa: Combatiente.desequipar.
	 */
	@Override
	public void quitarEfecto(Combatiente c) {
		c.modificarDanio(-dañoExtra);
	}

	/*
	 * Texto de la pieza: categoría, nombre y daño extra. Sirve para mostrar el equipo
	 * de un combatiente (por ejemplo, al imprimir getEquipamiento()).
	 */
	@Override
	public String toString() {
		return "Arma: " + super.getNombre() + " - Daño extra: " + dañoExtra;
	}

}
