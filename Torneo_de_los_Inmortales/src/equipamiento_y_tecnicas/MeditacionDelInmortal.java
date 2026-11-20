package equipamiento_y_tecnicas;

import efectos.AplicarEfectoTemporal;
import efectos.Curacion;
import efectos.EfectoCompuesto;
import efectos.Regeneracion;

/*
 * Técnica de recuperación: cuesta 20 de energía. COMPOSITE: cura 20 al instante y deja
 * una regeneración (BONUS: +5 de vida por turno durante 3 turnos).
 * La usan: el Monasterio en los tres main y el Palacio del Vacío en App y App3
 * (en App2 el Palacio la cambia por Veneno de la Serpiente). El criterio la elige
 * primero si al combatiente le falta vida.
 */
public class MeditacionDelInmortal extends Tecnica {

	public MeditacionDelInmortal() {
		super("Meditacion del Inmortal", 20, TipoTecnica.RECUPERACION, crearEfectoCompuesto());
	}

	/*
	 * Arma el efecto compuesto: curación inmediata más regeneración. Es static porque
	 * se llama dentro de super(...).
	 * Lo usa: el constructor de MeditacionDelInmortal.
	 */
	private static EfectoCompuesto crearEfectoCompuesto() {
		EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
		efectoCompuesto.agregar(new Curacion(20));
		efectoCompuesto.agregar(new AplicarEfectoTemporal(new Regeneracion(3, 5)));
		return efectoCompuesto;
	}
}
