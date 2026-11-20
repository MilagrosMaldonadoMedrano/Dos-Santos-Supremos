package equipamiento_y_tecnicas;

import efectos.AplicarEfectoTemporal;
import efectos.Daño;
import efectos.EfectoCompuesto;
import efectos.Veneno;

/*
 * Técnica ofensiva: cuesta 30 de energía. COMPOSITE: su efecto es un EfectoCompuesto
 * que hace 10 de daño al rival y lo envenena (BONUS: 7 de daño por turno durante 4 turnos).
 * El golpe pasa por las defensas del rival (Monje, armadura, Muro); el veneno las ignora.
 * La usan: el Palacio del Vacío en App2 (en lugar de Meditación) y en App3.
 */
public class VenenoDeLaSerpiente extends Tecnica {

    public VenenoDeLaSerpiente() {
        super("Veneno de la Serpiente Primordial", 30, TipoTecnica.OFENSIVA, crearEfectoCompuesto());
    }

    /*
     * Arma el efecto compuesto de la técnica (Composite). Es static porque se llama
     * dentro de super(...), antes de que exista el objeto. AplicarEfectoTemporal le da
     * al afectado una copia nueva del veneno cada vez que se usa la técnica.
     * Lo usa: el constructor de VenenoDeLaSerpiente.
     */
    private static EfectoCompuesto crearEfectoCompuesto() {
        EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
        efectoCompuesto.agregar(new Daño(10));
        efectoCompuesto.agregar(new AplicarEfectoTemporal(new Veneno(4, 7)));
        return efectoCompuesto;
    }

}
