package combatiente;

/*
 * Tipo de combatiente que gasta menos energía al ejecutar técnicas: paga un 30 % menos.
 * POLIMORFISMO: solo redefine calcularCostoEnergia; todo lo demás lo hereda de Combatiente.
 * Lo crea: CreadorCombatiente, con el tipo CreadorCombatiente.HECHICERO.
 */
public class HechiceroDelVacio extends Combatiente{
	private static final double MULTIPLICADOR_COSTO_ENERGIA = 0.7;   // paga el 70 % del costo

	/*
	 * Crea el hechicero con su identificador y su nombre (lo valida Combatiente).
	 * Lo usa: CreadorCombatiente.instanciar.
	 */
	public HechiceroDelVacio(int identificador, String nombre) {
		super(identificador, nombre);
	}

	/*
	 * POLIMORFISMO: multiplica el costo de energía por 0,7 y lo redondea.
	 * Lo usan: Combatiente.tieneEnergiaPara y gastarEnergia.
	 */
	@Override
	protected int calcularCostoEnergia(int costoBase) {
		return (int) Math.round(costoBase * MULTIPLICADOR_COSTO_ENERGIA);
	}
}
