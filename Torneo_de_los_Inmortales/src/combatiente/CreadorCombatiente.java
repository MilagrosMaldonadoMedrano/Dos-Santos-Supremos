package combatiente;

public class CreadorCombatiente {
	public static final String CULTIVADOR = "Cultivador de la Espada";
	public static final String MONJE = "Monje del Cuerpo Celestial";
	public static final String HECHICERO = "Hechicero del Vacío";

	private int proximoId = 1;

	public Combatiente crear(String tipo, String nombre) {
		Combatiente nuevo = instanciar(tipo, proximoId, nombre);
		proximoId++;
		return nuevo;
	}

	private Combatiente instanciar(String tipo, int id, String nombre) {
		if (tipo == null) {
			throw new IllegalArgumentException("El tipo no puede ser null.");
		}
		return switch (tipo) {
			case CULTIVADOR -> new CultivadorDeLaEspada(id, nombre);
			case MONJE -> new MonjeDelCuerpoCelestial(id, nombre);
			case HECHICERO -> new HechiceroDelVacio(id, nombre);
			default -> throw new IllegalArgumentException("Tipo de combatiente desconocido: " + tipo);
		};
	}
}
