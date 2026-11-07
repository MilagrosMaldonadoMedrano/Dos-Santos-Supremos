package efectos;

import combatiente.Combatiente;

public class Curacion implements Efecto{
	private int cantidad;
	
	public Curacion(int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public void aplicarEfecto(Combatiente afectado){
		afectado.curar(cantidad);
	}

}
