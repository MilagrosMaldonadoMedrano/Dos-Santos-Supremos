package combatiente;

/*
 * Tipo de combatiente que potencia el daño que provoca: pega un 25 % más.
 * POLIMORFISMO: solo redefine calcularDanioAtaque; todo lo demás lo hereda de Combatiente.
 * Lo crea: CreadorCombatiente, con el tipo CreadorCombatiente.CULTIVADOR.
 */
public class CultivadorDeLaEspada extends Combatiente{

	private static final double MULTIPLICADOR_DANIO = 1.25;   // +25 % de daño

	/*
	 * Crea el cultivador con su identificador y su nombre (lo valida Combatiente).
	 * Lo usa: CreadorCombatiente.instanciar.
	 */
	public CultivadorDeLaEspada(int identificador, String nombre) {
		super(identificador, nombre);
	}

	/*
	 * POLIMORFISMO: multiplica el daño que provoca por 1,25 y lo redondea.
	 * Lo usan: Combatiente.atacar e infligirDanio (daño de las técnicas).
	 */
	@Override
	protected int calcularDanioAtaque(int danio) {
		return (int) Math.round(danio * MULTIPLICADOR_DANIO);
	}

}
