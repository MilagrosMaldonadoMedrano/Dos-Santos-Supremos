package equipamiento_y_tecnicas;

import efectos.Daño;
import efectos.DrenarEnergia;
import efectos.EfectoCompuesto;

public class DominioDeLaNada extends Tecnica{
    
    public DominioDeLaNada (){
        super("Dominio de la Nada", 34, TipoTecnica.OFENSIVA, crearEfectoCompuesto());
    }

    private static EfectoCompuesto crearEfectoCompuesto(){
        EfectoCompuesto efectoCompuesto = new EfectoCompuesto();
        efectoCompuesto.agregar(new Daño(25));
        efectoCompuesto.agregar(new DrenarEnergia(15));
        return efectoCompuesto;
    }
	
}
