package combatiente;

public class MonjeDelCuerpoCelestial extends Combatiente{
	private static final double MULTIPLICADOR_DANIO_RECIBIDO = 0.75;

	public MonjeDelCuerpoCelestial(int identificador, String nombre) {
		super(identificador, nombre);
	}

	@Override
	protected int calcularDanioRecibido(int danio) {
		return (int) Math.round(danio * MULTIPLICADOR_DANIO_RECIBIDO);
	}
}
