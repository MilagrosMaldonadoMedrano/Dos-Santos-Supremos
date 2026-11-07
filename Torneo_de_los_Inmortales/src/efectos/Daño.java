package efectos;

import combatiente.Combatiente;

public class Daño implements Efecto{
	private int cantidad;
	
	public Daño (int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente objetivo) {
		
	}
}
