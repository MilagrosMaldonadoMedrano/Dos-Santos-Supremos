package interesado;

import combatiente.Combatiente;
import torneo.Pareja;

// Hecho del combate o del torneo. Cada subclase guarda los datos DEL MOMENTO
// en que ocurrió (§7): nombres, ids y vidas, no referencias que después cambian.
public abstract class Acontecimiento {
	private final int ronda;

	protected Acontecimiento(int ronda) {
		this.ronda = ronda;
	}

	public int getRonda() {
		return ronda;
	}

	// Cada acontecimiento llama al método que le corresponde del interesado.
	public abstract void notificarA(Interesado interesado);

	public abstract String descripcion();

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

	@Override
	public String toString() {
		return descripcion();
	}
}