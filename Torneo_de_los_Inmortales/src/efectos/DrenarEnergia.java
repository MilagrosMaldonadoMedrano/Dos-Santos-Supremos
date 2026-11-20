package efectos;

import combatiente.Combatiente;

/*
 * COMPOSITE (hoja): efecto que le quita energía al afectado.
 * Lo usa: DominioDeLaNada.
 */
public class DrenarEnergia implements Efecto{
	private int cantidad;   // energía que le quita

	/*
	 * Crea el efecto con la energía que quita.
	 * Lo usa: DominioDeLaNada.
	 */
	public DrenarEnergia(int cantidad) {
		this.cantidad = cantidad;
	}

	/*
	 * Le quita energía al afectado con Combatiente.perderEnergia, sin bajar de 0.
	 * Lo usan: Tecnica.realizar y EfectoCompuesto.
	 */
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		afectado.perderEnergia(cantidad);
	}


}
