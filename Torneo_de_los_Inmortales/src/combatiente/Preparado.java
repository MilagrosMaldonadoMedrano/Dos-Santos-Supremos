package combatiente;

/*
 * STATE: estado entre combates. El combatiente puede cambiar su equipo, pero no actúa,
 * no puede ser objetivo y no recibe daño. Todo combatiente nace en este estado y vuelve
 * a él al terminar cada combate (Combatiente.finalizarCombate).
 */
public class Preparado implements EstadoCombatiente{

	/*
	 * Entre combates no actúa.
	 * Lo usa: Combatiente.puedeActuar.
	 */
	public boolean puedeActuar() {
		return false;
	}

	/*
	 * Entre combates no puede ser objetivo.
	 * Lo usa: Combatiente.puedeSerObjetivo.
	 */
	public boolean puedeSerObjetivo() {
		return false;
	}

	/*
	 * Es el único estado que permite cambiar el equipo.
	 * Lo usa: Combatiente.validarCambioDeEquipo.
	 */
	public boolean puedeModificarEquipamiento() {
		return true;
	}

	/*
	 * Fuera de un combate no se puede recibir daño: lanza IllegalStateException.
	 * Lo usan: Combatiente.recibirDanio y recibirDanioDirecto.
	 */
	public void recibirDanio(Combatiente c, int danio) {
		throw new IllegalStateException( "No está en combate.");
	}
}
