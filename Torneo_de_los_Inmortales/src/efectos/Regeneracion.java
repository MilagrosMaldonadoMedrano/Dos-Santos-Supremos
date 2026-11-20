package efectos;

import combatiente.Combatiente;

/*
 * BONUS: efecto temporal que cura al afectado en cada uno de sus turnos, sin pasar
 * su vida máxima.
 * Lo usa: MeditacionDelInmortal (3 turnos de 5 de vida).
 */
public class Regeneracion extends EfectoTemporal{
	private int curacionPorTurno;

	/*
	 * Crea la regeneración con su duración y la vida que cura por turno.
	 * Lo usa: MeditacionDelInmortal.
	 */
	public Regeneracion(int turnos, int curacionPorTurno){
		super(turnos);
		this.curacionPorTurno = curacionPorTurno;
	}

	/*
	 * Crea una copia de otra regeneración (mismos turnos y curación).
	 * Lo usa: copiar.
	 */
	public Regeneracion (Regeneracion original){
		super(original);
		curacionPorTurno = original.curacionPorTurno;
	}

	/*
	 * Cura al afectado con Combatiente.curar.
	 * Lo usa: EfectoTemporal.aplicarPorTurno.
	 */
	@Override
	protected void ejecutarEfecto(Combatiente afectado) {
		afectado.curar(curacionPorTurno);
	}

	/*
	 * Devuelve una regeneración nueva igual a esta.
	 * Lo usa: AplicarEfectoTemporal.
	 */
	@Override
	public EfectoTemporal copiar() {
		return new Regeneracion(this);
	}
}
