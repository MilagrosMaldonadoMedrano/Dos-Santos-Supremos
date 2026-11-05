package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Arma extends Equipamiento{

	private int dañoExtra;
	
	public Arma(int dañoAumentado) {
		dañoExtra = dañoAumentado;
	}
	
	public int getDañoExtra() {
		return dañoExtra;
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
		return "Arma: " + super.getNombre() + " - Daño extra: " + dañoExtra;
	}

}
