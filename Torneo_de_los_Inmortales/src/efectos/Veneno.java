package efectos;

import combatiente.Combatiente;

/*
 * BONUS: efecto temporal que daña al afectado en cada uno de sus turnos. Ignora la
 * armadura y la defensa, y si deja la vida en 0 el combatiente queda fuera de combate.
 * Lo usan: DominioDeLaNada (3 turnos de 5 de daño) y VenenoDeLaSerpiente
 * (4 turnos de 7 de daño).
 */
public class Veneno extends EfectoTemporal{
	private int dañoPorTurno;

	/*
	 * Crea el veneno con su duración y el daño por turno.
	 * Lo usan: DominioDeLaNada y VenenoDeLaSerpiente.
	 */
	public Veneno(int turnos, int dañoPorTurno){
		super(turnos);
		this.dañoPorTurno = dañoPorTurno;
	}

	/*
	 * Crea una copia de otro veneno (mismos turnos y daño).
	 * Lo usa: copiar.
	 */
	public Veneno(Veneno original){
		super(original);
		this.dañoPorTurno = original.dañoPorTurno;
	}

	/*
	 * Daña al afectado con Combatiente.recibirDanioDirecto, que ignora la armadura,
	 * la defensa y la reducción del Monje.
	 * Lo usa: EfectoTemporal.aplicarPorTurno.
	 */
	@Override
	protected void ejecutarEfecto(Combatiente afectado) {
		afectado.recibirDanioDirecto(dañoPorTurno);
	}

	/*
	 * Devuelve un veneno nuevo igual a este.
	 * Lo usa: AplicarEfectoTemporal.
	 */
	@Override
	public EfectoTemporal copiar() {
		return new Veneno(this);
	}
}
