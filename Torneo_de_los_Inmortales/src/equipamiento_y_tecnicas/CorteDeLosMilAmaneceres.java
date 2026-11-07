package equipamiento_y_tecnicas;

import efectos.Daño;

public class CorteDeLosMilAmaneceres extends Tecnica{

	public CorteDeLosMilAmaneceres() {
		super("Corte de los Mil Amaneceres", 15, TipoTecnica.OFENSIVA, new Daño(20));
	}
}
