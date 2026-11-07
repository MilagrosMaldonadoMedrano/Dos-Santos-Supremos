package efectos;

import combatiente.Combatiente;

public abstract class EfectoTemporal {
	private int turnosRestantes;
	
	public EfectoTemporal(int turnos) {
		turnosRestantes = turnos;
	}

	public EfectoTemporal(EfectoTemporal efectoOriginal){
		this.turnosRestantes = efectoOriginal.turnosRestantes;
	}

	//este metodo se llama para aplicar el efecto cada turno
	public void aplicarPorTurno(Combatiente afectado){
		if(expiro())
			return;

		ejecutarEfecto(afectado);
		turnosRestantes--;
	}

	protected abstract void ejecutarEfecto(Combatiente afectado);

	private boolean expiro(){
		return  turnosRestantes <= 0;
	}

	public abstract EfectoTemporal copiar();
}
