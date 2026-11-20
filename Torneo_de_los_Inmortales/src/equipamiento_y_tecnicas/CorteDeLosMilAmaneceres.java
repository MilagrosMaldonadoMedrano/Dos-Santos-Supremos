package equipamiento_y_tecnicas;

import efectos.Daño;

/*
 * Técnica ofensiva: cuesta 15 de energía y hace 20 de daño al rival (Daño, efecto simple).
 * La usan: la Escuela de la Espada Celestial en el main y varias pruebas
 * (CorteDeLosMilAmaneceresTest, CombatienteTest, CombateTest).
 */
public class CorteDeLosMilAmaneceres extends Tecnica{

	public CorteDeLosMilAmaneceres() {
		super("Corte de los Mil Amaneceres", 15, TipoTecnica.OFENSIVA, new Daño(20));
	}
}
