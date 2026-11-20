package equipamiento_y_tecnicas;

import combatiente.Combatiente;

/*
 * Pieza de categoría ARMADURA: reduce el daño que recibe el combatiente mientras se lleva.
 * Subclase concreta: ArmaduraDorada.
 */
public class Armadura extends Equipamiento{
	private int armaduraExtra;   // daño que se descuenta de cada golpe recibido

	/*
	 * Crea una armadura con su nombre y cuánto daño reduce. El valor tiene que ser
	 * positivo; si no, lanza IllegalArgumentException.
	 * Lo usan: ArmaduraDorada (con super) y las pruebas.
	 */
	public Armadura(String nombre, int armaduraExtra) {
		super(nombre);
		if (armaduraExtra <= 0) throw new IllegalArgumentException("La armadura extra debe ser positiva");
		this.armaduraExtra = armaduraExtra;
	}

	/*
	 * Indica que esta pieza es una ARMADURA.
	 * Lo usa: Combatiente.equipar, para no permitir dos armaduras.
	 */
	public Categoria getCategoria() {
		return Categoria.ARMADURA; }

	/*
	 * Al equiparla, suma su valor a la reducción de daño del combatiente con
	 * modificarReduccionDanio: cada golpe que recibe le resta ese valor.
	 * Lo usa: Combatiente.equipar.
	 */
	public void aplicarEfecto(Combatiente c) {
		c.modificarReduccionDanio(armaduraExtra); }

	/*
	 * Al quitarla, resta la misma reducción que sumó.
	 * Lo usa: Combatiente.desequipar.
	 */
	public void quitarEfecto(Combatiente c)  {
		c.modificarReduccionDanio(-armaduraExtra); }

	/*
	 * Devuelve cuánto daño reduce la armadura.
	 * Lo usa: toString, para mostrar la pieza.
	 */
	public int getArmaduraExtra() {
		return armaduraExtra;
	}

	/*
	 * Texto de la pieza: categoría, nombre y reducción de daño.
	 */
	@Override
	public String toString() {
		return "Armadura: " + super.getNombre() + " - Armadura extra: " + armaduraExtra;
	}
}
