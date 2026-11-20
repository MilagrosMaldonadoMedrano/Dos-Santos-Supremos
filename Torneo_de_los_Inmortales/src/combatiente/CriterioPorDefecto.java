package combatiente;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import equipamiento_y_tecnicas.Tecnica;
import equipamiento_y_tecnicas.TipoTecnica;

/*
 * STRATEGY: criterio de acción por defecto, el que pide la consigna. Primero descarta las
 * técnicas que no se pueden usar (sin energía, sin usos, combatiente fuera de combate o
 * bloqueadas por el compañero). Después elige, en este orden: una de recuperación si al
 * combatiente le falta vida; una defensiva si no tiene una defensa activa; la ofensiva con
 * más usos restantes. Si ninguna es posible, devuelve vacío y se hace un ataque básico.
 * Los empates se resuelven por el orden de la escuela. Es determinista: ante el mismo
 * estado siempre elige lo mismo.
 * Lo usa: Combatiente, que lo tiene como criterio inicial.
 */
public class CriterioPorDefecto implements CriterioDeAccion {

	/*
	 * Arma la lista de técnicas posibles (recorridas en el orden de la escuela) y aplica
	 * los pasos del criterio: recuperación, defensiva, ofensiva con más usos, o vacío.
	 * Lo usa: Combatiente.elegirTecnica.
	 */
	@Override
	public Optional<Tecnica> elegir(Combatiente c, Set<Tecnica> bloqueadas) {

		List<Tecnica> posibles = new ArrayList<>();
		for (Tecnica t : c.getTecnicas()) {
			if (c.puedeUsar(t) && !bloqueadas.contains(t)) {
				posibles.add(t);
			}
		}

		Tecnica elegida = null;

		// Paso 1: si le falta vida, una de recuperación.
		if (c.getVidaActual() < c.getVidaMaxima()) {
			elegida = primeraDeTipo(posibles, TipoTecnica.RECUPERACION);
		}

		// Paso 2: si todavía no eligió y no tiene defensa activa, una defensiva.
		if (elegida == null && !c.tieneDefensaActiva()) {
			elegida = primeraDeTipo(posibles, TipoTecnica.DEFENSIVA);
		}

		// Paso 3: si todavía no eligió, la ofensiva con más usos restantes.
		if (elegida == null) {
			elegida = ofensivaConMasUsos(c, posibles);
		}

		// Paso 4: si sigue en null, no hay técnica posible y se hace un ataque básico.
		return Optional.ofNullable(elegida);
	}

	/*
	 * Devuelve la primera técnica del tipo pedido, respetando el orden de la escuela,
	 * o null si no hay ninguna.
	 * Lo usa: elegir (pasos 1 y 2).
	 */
	private Tecnica primeraDeTipo(List<Tecnica> tecnicas, TipoTecnica tipo) {
		for (Tecnica t : tecnicas) {
			if (t.getTipoTecnica() == tipo) {
				return t;
			}
		}
		return null;
	}

	/*
	 * Devuelve la ofensiva con más usos restantes, o null si no hay ninguna.
	 * Usa ">" (y no ">="), así ante un empate se queda con la primera de la escuela.
	 * Lo usa: elegir (paso 3).
	 */
	private Tecnica ofensivaConMasUsos(Combatiente c, List<Tecnica> tecnicas) {
		Tecnica mejor = null;
		for (Tecnica t : tecnicas) {
			if (t.getTipoTecnica() == TipoTecnica.OFENSIVA) {
				if (mejor == null || c.usosRestantes(t) > c.usosRestantes(mejor)) {
					mejor = t;
				}
			}
		}
		return mejor;
	}
}
