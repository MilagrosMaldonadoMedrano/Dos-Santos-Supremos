package equipamiento_y_tecnicas;

import combatiente.Combatiente;

/*
 * Pieza de categoría SELLO: aumenta la vida máxima del combatiente mientras se lleva.
 * Subclase concreta: SelloDelFenix.
 */
public class Sello extends Equipamiento{
	private int vidaExtra;   // vida máxima que suma

	/*
	 * Crea un sello con su nombre y la vida máxima que suma. El valor tiene que ser
	 * positivo; si no, lanza IllegalArgumentException.
	 * Lo usan: SelloDelFenix (con super) y las pruebas.
	 */
	public Sello(String nombre, int vidaExtra) {
		super(nombre);
		if (vidaExtra <= 0) throw new IllegalArgumentException("La vida extra debe ser positiva");
		this.vidaExtra = vidaExtra;
	}

	/*
	 * Indica que esta pieza es un SELLO.
	 * Lo usa: Combatiente.equipar, para no permitir dos sellos.
	 */
	public Categoria getCategoria() {
		return Categoria.SELLO; }

	/*
	 * Al equiparlo, sube la vida máxima con modificarVidaMaxima. La vida actual no sube;
	 * se llena al empezar el combate.
	 * Lo usa: Combatiente.equipar.
	 */
	public void aplicarEfecto(Combatiente c) {
		c.modificarVidaMaxima(vidaExtra); }

	/*
	 * Al quitarlo, baja la vida máxima; si la actual queda por encima, se recorta.
	 * Lo usa: Combatiente.desequipar. Lo prueba SelloDelFenixTest.
	 */
	public void quitarEfecto(Combatiente c)  {
		c.modificarVidaMaxima(-vidaExtra); }

	/*
	 * Devuelve cuánta vida máxima suma el sello.
	 * Lo usan: toString y SelloDelFenixTest.
	 */
	public int getVidaExtra() {
		return vidaExtra;
	}

	/*
	 * Texto de la pieza: categoría, nombre y vida extra.
	 */
	@Override
	public String toString() {
		return "Sello: " + super.getNombre() + " - Vida extra: " + vidaExtra;
	}
}
