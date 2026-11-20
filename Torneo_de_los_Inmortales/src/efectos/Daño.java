package efectos;

import combatiente.Combatiente;

/*
 * COMPOSITE (hoja): efecto que daña al afectado.
 * Lo usan: CorteDeLosMilAmaneceres, RugidoDeLaBestiaPrimordial y DominioDeLaNada.
 */
public class Daño implements Efecto{
	private int cantidad;   // daño base de la técnica

	/*
	 * Crea el efecto con su daño base.
	 * Lo usan: las técnicas ofensivas en su constructor.
	 */
	public Daño (int cantidad) {
		this.cantidad = cantidad;
	}

	/*
	 * POLIMORFISMO: el ejecutor calcula cuánto daño hace con infligirDanio (el Cultivador
	 * pega más) y el afectado lo recibe con recibirDanio (el Monje recibe menos).
	 * Lo usan: Tecnica.realizar y EfectoCompuesto.
	 */
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		afectado.recibirDanio(ejecutor.infligirDanio(cantidad));
	}
}
