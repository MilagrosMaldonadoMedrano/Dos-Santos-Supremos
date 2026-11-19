package torneo;

import java.util.Objects;

import interesado.Notificador;

// Enfrentamiento entre dos parejas dentro de una ronda; se resuelve con un combate.
public class Cruce {
	private final Pareja parejaA;
	private final Pareja parejaB;
	private Combate combate;   // se crea al resolver

	public Cruce(Pareja parejaA, Pareja parejaB) {
		this.parejaA = Objects.requireNonNull(parejaA, "La pareja no puede ser null");
		this.parejaB = Objects.requireNonNull(parejaB, "La pareja no puede ser null");
	}

	public Pareja resolver(int ronda, Notificador notificador) {
		if (combate != null) {
			throw new IllegalStateException("Este cruce ya se resolvió");
		}
		combate = new Combate(parejaA, parejaB, ronda, notificador);
		return combate.jugar();
	}

	public Pareja getGanador() {
		return combate == null ? null : combate.getGanadora();
	}

	public Pareja getParejaA() { return parejaA; }
	public Pareja getParejaB() { return parejaB; }
	public Combate getCombate() { return combate; }
}