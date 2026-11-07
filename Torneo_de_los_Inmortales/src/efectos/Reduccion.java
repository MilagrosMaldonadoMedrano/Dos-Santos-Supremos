package efectos;

import combatiente.Combatiente;

public class Reduccion implements Efecto{
	private int reduccion;
	public Reduccion (int reduccion) {
		this.reduccion = reduccion;
	}
	
	@Override
	public void aplicarEfecto(Combatiente afectado) {
		afectado.setReduccionDefensiva(reduccion);
	}
}
