package combatiente;

/*
 * STATE: define lo que un combatiente puede hacer según el momento en que está.
 * Combatiente guarda un EstadoCombatiente y le pregunta en lugar de usar ifs.
 * Implementaciones: Preparado (entre combates), EnPie (en combate) y FueraDeCombate (caído).
 */
public interface EstadoCombatiente {

	/*
	 * Indica si el combatiente puede actuar (atacar, usar técnicas, curarse).
	 * Lo usa: Combatiente.puedeActuar.
	 */
	boolean puedeActuar();

	/*
	 * Indica si el combatiente puede ser elegido como objetivo.
	 * Lo usa: Combatiente.puedeSerObjetivo.
	 */
	boolean puedeSerObjetivo();

	/*
	 * Indica si el combatiente puede equipar o quitar piezas.
	 * Lo usa: Combatiente.validarCambioDeEquipo.
	 */
	boolean puedeModificarEquipamiento();

	/*
	 * Decide qué pasa cuando el combatiente recibe daño en este estado.
	 * Lo usan: Combatiente.recibirDanio y recibirDanioDirecto.
	 */
	void recibirDanio(Combatiente c,int danio);
}
