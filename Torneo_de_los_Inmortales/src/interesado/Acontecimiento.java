package interesado;

import combatiente.Combatiente;
import torneo.Pareja;

/*
 * OBSERVER: clase abstracta de todo hecho del combate o del torneo. Cada subclase guarda
 * los datos DEL MOMENTO en que ocurrió (nombres, ids y vidas como valores), no
 * referencias a objetos que después cambian.
 * Subclases: InicioCombate, AccionRealizada, Eliminacion, ResultadoCombate y Proclamacion.
 */
public abstract class Acontecimiento {
	private final int ronda;

	/*
	 * Guarda la ronda en la que ocurrió.
	 * Lo usan: los constructores de las cinco subclases (con super).
	 */
	protected Acontecimiento(int ronda) {
		this.ronda = ronda;
	}

	/*
	 * Devuelve la ronda en la que ocurrió.
	 * Lo usan: CronistaDeLaSecta (para la tabla de posiciones), las descripciones
	 * de las subclases y las pruebas.
	 */
	public int getRonda() {
		return ronda;
	}

	/*
	 * POLIMORFISMO: cada acontecimiento llama al método que le corresponde del interesado
	 * (por ejemplo, Eliminacion llama a interesado.eliminacion(this)).
	 * Lo usa: Notificador.publicar.
	 */
	public abstract void notificarA(Interesado interesado);

	/*
	 * POLIMORFISMO: texto que describe el acontecimiento.
	 * Lo usan: HeraldoImperial (lo muestra por pantalla) y toString.
	 */
	public abstract String descripcion();

	/*
	 * Arma el texto con los nombres de los integrantes de una pareja ("Aurelio y Nébula").
	 * Es static porque no depende de ningún acontecimiento en particular.
	 * Lo usan: InicioCombate, ResultadoCombate y Proclamacion.
	 */
	protected static String integrantes(Pareja pareja) {
		StringBuilder nombres = new StringBuilder();
		for (Combatiente c : pareja.getCombatientes()) {
			if (nombres.length() > 0) {
				nombres.append(" y ");
			}
			nombres.append(c.getNombre());
		}
		return nombres.toString();
	}

	/*
	 * Devuelve la descripción; permite comparar historiales completos en las pruebas.
	 */
	@Override
	public String toString() {
		return descripcion();
	}
}
