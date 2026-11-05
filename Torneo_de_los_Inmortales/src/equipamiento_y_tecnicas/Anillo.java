package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Anillo extends Equipamiento{
	private int energiaExtra;
	
	public Anillo(int energiaAumentada) {
		energiaExtra = energiaAumentada;
	}

	public int getEnergiaExtra() {
		return energiaExtra;
	}

	@Override
	public void aplicarEfecto(Combatiente c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitarEfecto(Combatiente c) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "Anillo: " + super.getNombre() + " - Energía extra: " + energiaExtra;
	}
}
