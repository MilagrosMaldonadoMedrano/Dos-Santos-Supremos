package equipamiento_y_tecnicas;

import efectos.AplicarEfectoTemporal;
import efectos.Daño;
import efectos.DrenarEnergia;
import efectos.EfectoCompuesto;
import efectos.Veneno;

/*
 * Técnica ofensiva: cuesta 34 de energía. COMPOSITE: su efecto es un EfectoCompuesto
 * que hace 25 de daño, le quita 15 de energía al rival y lo envenena
 * (BONUS: 5 de daño por turno durante 3 turnos).
 * La usa: el Palacio del Vacío en el main.
 */
public class DominioDeLaNada extends Tecnica {

	public DominioDeLaNada() {
		super("Dominio de la Nada", 34, TipoTecnica.OFENSIVA, crearEfectoCompuesto());
	}

	/*
	 * Arma el efecto compuesto de la técnica (Composite). Es static porque se llama
	 * dentro de super(...), antes de que exista el objeto. AplicarEfectoTemporal le da
	 * al afectado una copia nueva del veneno cada vez que se usa la técnica.
	 * Lo usa: el constructor de DominioDeLaNada.
	 */
	private static EfectoCompuesto crearEfectoCompuesto() {
		EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
		efectoCompuesto.agregar(new Daño(25));
		efectoCompuesto.agregar(new DrenarEnergia(15));
		efectoCompuesto.agregar(new AplicarEfectoTemporal(new Veneno(3, 5)));
		return efectoCompuesto;
	}
}
