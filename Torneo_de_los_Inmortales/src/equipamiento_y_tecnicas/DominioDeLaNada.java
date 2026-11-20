package equipamiento_y_tecnicas;

import efectos.AplicarEfectoTemporal;
import efectos.Daño;
import efectos.DrenarEnergia;
import efectos.EfectoCompuesto;
import efectos.Veneno;

// Ofensiva: daña al rival, le quita energía y lo ENVENENA (bonus: efecto temporal).
public class DominioDeLaNada extends Tecnica {

	public DominioDeLaNada() {
		super("Dominio de la Nada", 34, TipoTecnica.OFENSIVA, crearEfectoCompuesto());
	}

	// Composite: un solo efecto que agrupa tres.
	private static EfectoCompuesto crearEfectoCompuesto() {
		EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
		efectoCompuesto.agregar(new Daño(25));
		efectoCompuesto.agregar(new DrenarEnergia(15));
		// Veneno: 5 de daño por turno del afectado, durante 3 turnos.
		// AplicarEfectoTemporal le da al afectado una COPIA nueva cada vez que se usa la técnica.
		efectoCompuesto.agregar(new AplicarEfectoTemporal(new Veneno(3, 5)));
		return efectoCompuesto;
	}
}