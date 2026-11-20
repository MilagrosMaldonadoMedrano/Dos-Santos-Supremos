package equipamiento_y_tecnicas;

import efectos.AplicarEfectoTemporal;
import efectos.Daño;
import efectos.EfectoCompuesto;
import efectos.Veneno;

public class VenenoDeLaSerpiente extends Tecnica {

    public VenenoDeLaSerpiente() {
        super("Veneno de la Serpiente Primordial", 30, TipoTecnica.OFENSIVA, crearEfectoCompuesto());
    }

    private static EfectoCompuesto crearEfectoCompuesto() {
        EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
        efectoCompuesto.agregar(new Daño(10));
        efectoCompuesto.agregar(new AplicarEfectoTemporal(new Veneno(4, 7)));
        return efectoCompuesto;
    }

}
