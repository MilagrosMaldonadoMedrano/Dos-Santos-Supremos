package efectos;

import combatiente.Combatiente;

public class Reduccion implements Efecto{
	private int reduccion;
	public Reduccion (int reduccion) {
		this.reduccion = reduccion;
	}
	
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		afectado.activarDefensa(reduccion);
	}
}
