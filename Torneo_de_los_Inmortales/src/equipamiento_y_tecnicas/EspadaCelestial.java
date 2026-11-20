package equipamiento_y_tecnicas;

/*
 * Pieza concreta del main de ejemplo: un Arma llamada "Espada celestial" que suma 5 de daño.
 * Hereda la categoría ARMA y el efecto de Arma; solo fija el nombre y el valor.
 * La usan: la Escuela de la Espada Celestial en los main y las pruebas de equipamiento.
 */
public class EspadaCelestial  extends Arma  {
	public EspadaCelestial() { super("Espada celestial", 5); }
}
