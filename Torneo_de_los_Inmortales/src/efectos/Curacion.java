package efectos;

import combatiente.Combatiente;

/*
 * COMPOSITE (hoja): efecto que cura al afectado al instante.
 * Lo usa: MeditacionDelInmortal (y CuracionTest).
 */
public class Curacion implements Efecto{
	private int cantidad;   // vida que recupera

	/*
	 * Crea la curación con la vida que recupera.
	 * Lo usa: MeditacionDelInmortal.
	 */
	public Curacion(int cantidad) {
		this.cantidad = cantidad;
	}

	/*
	 * Cura al afectado con Combatiente.curar, sin pasar su vida máxima. El ejecutor no se
	 * usa: la curación no depende de quién la hace.
	 * Lo usan: Tecnica.realizar y EfectoCompuesto.
	 */
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		afectado.curar(cantidad);
	}

}
