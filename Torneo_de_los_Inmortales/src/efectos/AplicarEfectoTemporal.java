package efectos;

import combatiente.Combatiente;

/*
 * COMPOSITE (hoja) y BONUS: efecto que le deja al afectado un efecto temporal
 * (veneno o regeneración). Guarda un efecto "modelo" y cada vez que se aplica le
 * entrega al afectado una copia nueva, para que cada uso cuente sus propios turnos.
 * Lo usan: DominioDeLaNada (veneno) y MeditacionDelInmortal (regeneración).
 */
public class AplicarEfectoTemporal implements Efecto{
	private EfectoTemporal prototipo;   // efecto modelo que se copia en cada uso

	/*
	 * Guarda el efecto temporal que se va a copiar.
	 * Lo usan: DominioDeLaNada y MeditacionDelInmortal.
	 */
	public AplicarEfectoTemporal(EfectoTemporal efectoTemporal){
		prototipo = efectoTemporal;
	}

	/*
	 * Copia el efecto modelo y se lo agrega al afectado con agregarEfectoTemporal.
	 * Lo usan: Tecnica.realizar y EfectoCompuesto.
	 */
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		EfectoTemporal copia = prototipo.copiar();
		afectado.agregarEfectoTemporal(copia);
	}
}
