package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Sello extends Equipamiento{
	private int vidaExtra;
	
	public Sello(int vida) {
		vidaExtra = vida;
	}

	public int getVidaExtra() {
		return vidaExtra;
	}

	@Override
	public void aplicarEfecto(Combatiente c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitarEfecto(Combatiente c) {
		// TODO Auto-generated method stub
		
	}

}
