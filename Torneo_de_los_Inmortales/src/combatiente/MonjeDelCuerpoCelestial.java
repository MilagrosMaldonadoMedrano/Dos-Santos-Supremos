package combatiente;

/*
 * Tipo de combatiente que reduce el daño que recibe: recibe un 25 % menos.
 * POLIMORFISMO: solo redefine calcularDanioRecibido; todo lo demás lo hereda de Combatiente.
 * Lo crea: CreadorCombatiente, con el tipo CreadorCombatiente.MONJE.
 */
public class MonjeDelCuerpoCelestial extends Combatiente{
	private static final double MULTIPLICADOR_DANIO_RECIBIDO = 0.75;   // recibe el 75 % del daño

	/*
	 * Crea el monje con su identificador y su nombre (lo valida Combatiente).
	 * Lo usa: CreadorCombatiente.instanciar.
	 */
	public MonjeDelCuerpoCelestial(int identificador, String nombre) {
		super(identificador, nombre);
	}

	/*
	 * POLIMORFISMO: multiplica el daño recibido por 0,75 y lo redondea.
	 * Lo usa: Combatiente.recibirDanio, antes de restar la armadura y la defensa.
	 */
	@Override
	protected int calcularDanioRecibido(int danio) {
		return (int) Math.round(danio * MULTIPLICADOR_DANIO_RECIBIDO);
	}
}
