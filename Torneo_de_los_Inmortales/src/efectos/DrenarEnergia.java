package efectos;

import combatiente.Combatiente;

public class DrenarEnergia implements Efecto{
	private int cantidad;
	
	public DrenarEnergia(int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public void aplicarEfecto(Combatiente afectado) {
		afectado.perderEnergia(cantidad);
	}


}
