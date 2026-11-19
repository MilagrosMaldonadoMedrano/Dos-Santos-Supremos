package equipamiento_y_tecnicas;

import efectos.AplicarEfectoTemporal;
import efectos.Curacion;
import efectos.EfectoCompuesto;
import efectos.Regeneracion;

// Recuperación: cura al instante y deja una REGENERACIÓN (bonus: efecto temporal).
public class MeditacionDelInmortal extends Tecnica {

	public MeditacionDelInmortal() {
		super("Meditacion del Inmortal", 20, TipoTecnica.RECUPERACION, crearEfectoCompuesto());
	}

	private static EfectoCompuesto crearEfectoCompuesto() {
		EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
		efectoCompuesto.agregar(new Curacion(20));
		// Regeneración: +5 de vida por turno del ejecutor, durante 3 turnos.
		efectoCompuesto.agregar(new AplicarEfectoTemporal(new Regeneracion(3, 5)));
		return efectoCompuesto;
	}
}