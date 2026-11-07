package equipamiento_y_tecnicas;

import combatiente.Combatiente;
import efectos.Daño;
import efectos.Efecto;

public class CorteDeLosMilAmaneceres extends Tecnica{

	public CorteDeLosMilAmaneceres() {
		super("Corte de los mil Amaneceres", 15, TipoTecnica.OFENSIVA, new Daño(20));
	}

	@Override
	public void realizar(Combatiente ejecutor, Combatiente objetivo) {
		
	}
	
}
