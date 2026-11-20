package combatiente;

/*
 * STATE: estado de un combatiente caído (su vida llegó a 0). No actúa ni puede ser
 * objetivo hasta el combate siguiente, cuando prepararParaCombate lo vuelve a EnPie.
 * Se entra desde EnPie.recibirDanio.
 */
public class FueraDeCombate implements EstadoCombatiente{

	/*
	 * Un caído no actúa.
	 * Lo usa: Combatiente.puedeActuar (y por eso IteradorEnPie lo saltea).
	 */
	public boolean puedeActuar() {
		return false;
	}

	/*
	 * Un caído no puede ser elegido como objetivo.
	 * Lo usa: Combatiente.puedeSerObjetivo.
	 */
	public boolean puedeSerObjetivo() {
		return false;
	}

	/*
	 * Durante el combate no se puede cambiar el equipo.
	 * Lo usa: Combatiente.validarCambioDeEquipo.
	 */
	public boolean puedeModificarEquipamiento(){
		return false;
	}

	/*
	 * Un caído no puede recibir más daño: lanza IllegalStateException.
	 * Lo usan: Combatiente.recibirDanio y recibirDanioDirecto.
	 */
	public void recibirDanio(Combatiente c, int danio) {
		throw new IllegalStateException("No está en combate.");
	}

}
