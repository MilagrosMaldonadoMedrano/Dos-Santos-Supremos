package torneo;

import java.util.Objects;

import interesado.Notificador;

/*
 * Enfrentamiento entre dos parejas dentro de una ronda; se resuelve con un combate.
 * Lo crea: Ronda, al emparejar las parejas en orden.
 */
public class Cruce {
	private final Pareja parejaA;
	private final Pareja parejaB;
	private Combate combate;   // se crea al resolver

	/*
	 * Crea el cruce entre dos parejas. Si alguna es null, lanza NullPointerException.
	 * Lo usa: el constructor de Ronda.
	 */
	public Cruce(Pareja parejaA, Pareja parejaB) {
		this.parejaA = Objects.requireNonNull(parejaA, "La pareja no puede ser null");
		this.parejaB = Objects.requireNonNull(parejaB, "La pareja no puede ser null");
	}

	/*
	 * Crea el combate entre las dos parejas, lo juega y devuelve la ganadora.
	 * Si el cruce ya se resolvió, lanza IllegalStateException.
	 * Lo usa: Ronda.jugar.
	 */
	public Pareja resolver(int ronda, Notificador notificador) {
		if (combate != null) {
			throw new IllegalStateException("Este cruce ya se resolvió");
		}
		combate = new Combate(parejaA, parejaB, ronda, notificador);
		return combate.jugar();
	}

	/*
	 * Devuelve la pareja ganadora, o null si el cruce todavía no se jugó.
	 * Hoy no lo usa ninguna clase; queda para consultar el resultado del cruce.
	 */
	public Pareja getGanador() {
		return combate == null ? null : combate.getGanadora();
	}

	/*
	 * Consultas del cruce: las dos parejas y el combate (null si no se jugó).
	 * Hoy no las usa ninguna clase; sirven para verificar el orden de los cruces.
	 */
	public Pareja getParejaA() { return parejaA; }
	public Pareja getParejaB() { return parejaB; }
	public Combate getCombate() { return combate; }
}
