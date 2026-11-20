package equipamiento_y_tecnicas;

import combatiente.Combatiente;

/*
 * Pieza de categoría ANILLO: aumenta la energía máxima del combatiente mientras se lleva.
 * Subclase concreta: AnilloEspiritual.
 */
public class Anillo extends Equipamiento{
	private int energiaExtra;   // energía máxima que suma

	/*
	 * Crea un anillo con su nombre y la energía máxima que suma. El valor tiene que ser
	 * positivo; si no, lanza IllegalArgumentException.
	 * Lo usan: AnilloEspiritual (con super) y las pruebas.
	 */
	public Anillo(String nombre, int energiaExtra) {
		super(nombre);
		if (energiaExtra <= 0) throw new IllegalArgumentException("La energía extra debe ser positiva");
		this.energiaExtra = energiaExtra;
	}

	/*
	 * Indica que esta pieza es un ANILLO.
	 * Lo usa: Combatiente.equipar, para no permitir dos anillos.
	 */
	public Categoria getCategoria() {
		return Categoria.ANILLO; }

	/*
	 * Al equiparlo, sube la energía máxima con modificarEnergiaMaxima. La energía actual
	 * no sube; se llena al empezar el combate.
	 * Lo usa: Combatiente.equipar.
	 */
	public void aplicarEfecto(Combatiente c) {
		c.modificarEnergiaMaxima(energiaExtra); }

	/*
	 * Al quitarlo, baja la energía máxima; si la actual queda por encima, se recorta.
	 * Lo usa: Combatiente.desequipar.
	 */
	public void quitarEfecto(Combatiente c)  {
		c.modificarEnergiaMaxima(-energiaExtra); }

	/*
	 * Devuelve cuánta energía máxima suma el anillo.
	 * Lo usa: toString, para mostrar la pieza.
	 */
	public int getEnergiaExtra() {
		return energiaExtra;
	}

	/*
	 * Texto de la pieza: categoría, nombre y energía extra.
	 */
	@Override
	public String toString() {
		return "Anillo: " + super.getNombre() + " - Energía extra: " + energiaExtra;
	}
}
