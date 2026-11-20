package efectos;

import combatiente.Combatiente;

/*
 * COMPOSITE (hoja): efecto defensivo que reduce el daño que recibe el afectado durante
 * el resto del combate.
 * Lo usa: MuroDelCuerpoDorado.
 */
public class Reduccion implements Efecto{
	private int reduccion;   // daño que se descuenta de cada golpe

	/*
	 * Crea el efecto con la reducción de daño.
	 * Lo usa: MuroDelCuerpoDorado.
	 */
	public Reduccion (int reduccion) {
		this.reduccion = reduccion;
	}

	/*
	 * Activa la defensa en el afectado (el que usa la técnica) con
	 * Combatiente.activarDefensa; se quita sola al terminar el combate.
	 * Lo usan: Tecnica.realizar y EfectoCompuesto.
	 */
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		afectado.activarDefensa(reduccion);
	}
}
