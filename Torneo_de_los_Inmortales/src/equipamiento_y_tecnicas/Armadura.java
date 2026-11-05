package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Armadura extends Equipamiento{
	private int armaduraExtra;
	
	public Armadura(int armaduraAumentada) {
		armaduraExtra = armaduraAumentada;
	}
	
	public int getArmaduraExtra() {
		return armaduraExtra;
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
		return "Armadura: " + super.getNombre() + " - Armadura extra: " + armaduraExtra;
	}
}
