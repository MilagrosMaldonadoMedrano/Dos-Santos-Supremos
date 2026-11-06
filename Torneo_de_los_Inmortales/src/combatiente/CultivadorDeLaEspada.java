package combatiente;

public class CultivadorDeLaEspada extends Combatiente{
	
	
	private static final double MULTIPLICADOR_DANIO = 1.25;

	public CultivadorDeLaEspada(int identificador, String nombre) {
		super(identificador, nombre);
	}

	@Override
	protected int calcularDanioAtaque(int danio) {
		return (int) Math.round(danio * MULTIPLICADOR_DANIO);
	}
	
}
