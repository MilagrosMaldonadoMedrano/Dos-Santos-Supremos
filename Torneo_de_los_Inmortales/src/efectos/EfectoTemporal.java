package efectos;

import combatiente.Combatiente;

/*
 * BONUS: clase abstracta de los efectos que duran varios turnos del combatiente afectado.
 * Cuenta los turnos que le quedan y, en cada turno, ejecuta su efecto. Cuando se le
 * terminan los turnos, Combatiente lo quita de su lista.
 * Subclases: Veneno y Regeneracion.
 */
public abstract class EfectoTemporal {
	private int turnosRestantes;

	/*
	 * Crea el efecto con la cantidad de turnos que dura.
	 * Lo usan: los constructores de Veneno y Regeneracion.
	 */
	public EfectoTemporal(int turnos) {
		turnosRestantes = turnos;
	}

	/*
	 * Crea una copia de otro efecto, con los mismos turnos.
	 * Lo usan: los constructores de copia de Veneno y Regeneracion (desde copiar).
	 */
	public EfectoTemporal(EfectoTemporal efectoOriginal){
		this.turnosRestantes = efectoOriginal.turnosRestantes;
	}

	/*
	 * Si todavía le quedan turnos, ejecuta el efecto y descuenta un turno.
	 * Lo usa: Combatiente.procesarEfectosTurno, al comenzar cada turno del afectado.
	 */
	public void aplicarPorTurno(Combatiente afectado){
		if(expiro())
			return;

		ejecutarEfecto(afectado);
		turnosRestantes--;
	}

	/*
	 * POLIMORFISMO: lo que hace el efecto en cada turno. Lo define cada subclase:
	 * Veneno daña y Regeneracion cura.
	 * Lo usa: aplicarPorTurno.
	 */
	protected abstract void ejecutarEfecto(Combatiente afectado);

	/*
	 * Indica si ya no le quedan turnos.
	 * Lo usan: aplicarPorTurno y Combatiente.procesarEfectosTurno (para quitar los vencidos).
	 */
	public boolean expiro(){
		return  turnosRestantes <= 0;
	}

	/*
	 * Devuelve una copia nueva del efecto, con todos sus turnos.
	 * Lo usa: AplicarEfectoTemporal, cada vez que una técnica aplica el efecto.
	 */
	public abstract EfectoTemporal copiar();
}
