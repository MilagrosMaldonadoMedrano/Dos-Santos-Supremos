package combatiente;

public class HechiceroDelVacio extends Combatiente{
	private static final double MULTIPLICADOR_COSTO_ENERGIA = 0.7;

	public HechiceroDelVacio(int identificador, String nombre) {
		super(identificador, nombre);
	}

	@Override
	protected int calcularCostoEnergia(int costoBase) {
		return (int) Math.round(costoBase * MULTIPLICADOR_COSTO_ENERGIA);
	}
}
