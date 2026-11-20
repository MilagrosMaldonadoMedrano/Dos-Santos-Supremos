package combatiente;

/*
 * STATE: estado durante un combate, mientras el combatiente tiene vida. Actúa, puede ser
 * objetivo y no puede cambiar el equipo. Si su vida llega a 0, este estado lo pasa
 * a FueraDeCombate. Se entra con Combatiente.prepararParaCombate.
 */
public class EnPie implements EstadoCombatiente{

	/*
	 * En pie puede actuar.
	 * Lo usa: Combatiente.puedeActuar.
	 */
	public boolean puedeActuar() {
		return true;
	}

	/*
	 * Durante el combate no se puede cambiar el equipo.
	 * Lo usa: Combatiente.validarCambioDeEquipo.
	 */
	public boolean puedeModificarEquipamiento() {
		return false;
	}

	/*
	 * En pie puede ser elegido como objetivo.
	 * Lo usa: Combatiente.puedeSerObjetivo.
	 */
	public boolean puedeSerObjetivo() {
		return true;
	}

	/*
	 * Le resta la vida al combatiente y, si queda en 0, lo pasa a FueraDeCombate.
	 * Es el único que usa perderVida y que cambia el estado a caído.
	 * Lo usan: Combatiente.recibirDanio y recibirDanioDirecto.
	 */
	public void recibirDanio(Combatiente c, int danio) {
		c.perderVida(danio);
		if(c.getVidaActual()==0) {
			c.cambiarEstado(new FueraDeCombate());
		}
	}

}
