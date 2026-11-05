package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public abstract class Equipamiento {
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}
	
	public abstract void aplicarEfecto(Combatiente c);
	public abstract void quitarEfecto(Combatiente c);
}
