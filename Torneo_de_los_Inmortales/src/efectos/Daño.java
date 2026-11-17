package efectos;

import combatiente.Combatiente;

public class Daño implements Efecto{
	private int cantidad;
	
	public Daño (int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	/*
	 * le sume el ejecutor ya que con este calculo cuanto danio hace con la tecnica 
	 */
	
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
				afectado.recibirDanio(ejecutor.infligirDanio(cantidad));
	}
}
