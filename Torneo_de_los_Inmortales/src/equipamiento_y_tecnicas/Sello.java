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
	///agregar un toString que muestre el nombre del sello y la vida extra que da
	@Override
	public String toString() {
		return "Sello: " + super.getNombre() + " - Vida extra: " + vidaExtra;
	}
}
