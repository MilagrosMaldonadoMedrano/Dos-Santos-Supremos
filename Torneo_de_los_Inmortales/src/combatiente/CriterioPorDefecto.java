package combatiente;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import equipamiento_y_tecnicas.Tecnica;
import equipamiento_y_tecnicas.TipoTecnica;



public class CriterioPorDefecto implements CriterioDeAccion {

	// armardo de la lista de tcenicas que puede usar ahora
	// (con energia, con usos, en pie y no bloqueadas por el compañero)
	// Se recorren en el orden de la escuela, asiqque "posibles" mantiene ese orden.
	@Override
	public Optional<Tecnica> elegir(Combatiente c, Set<Tecnica> bloqueadas) {
		
		List<Tecnica> posibles = new ArrayList<>();
		for (Tecnica t : c.getTecnicas()) {
			if (c.puedeUsar(t) && !bloqueadas.contains(t)) {
				posibles.add(t);
			}
		}

		
		Tecnica elegida = null;

		// 1: si le falta vida,una de recuperacion.
		if (c.getVidaActual() < c.getVidaMaxima()) {
			elegida = primeraDeTipo(posibles, TipoTecnica.RECUPERACION);
		}

		//2: si todavía no eligio y no tiene defensa activa, una defensiva.
		if (elegida == null && !c.tieneDefensaActiva()) {
			elegida = primeraDeTipo(posibles, TipoTecnica.DEFENSIVA);
		}

		//3: si todavia no eligio, la ofensiva con mas usos restantes.
		if (elegida == null) {
			elegida = ofensivaConMasUsos(c, posibles);
		}

		//4: si sigue en null, no hay técnica posible ( ataque básico).
		return Optional.ofNullable(elegida);
	}

	// Devuelve la primera tecnica del tipo pedido (respeta el orden de la escuela), o null.
	private Tecnica primeraDeTipo(List<Tecnica> tecnicas, TipoTecnica tipo) {
		for (Tecnica t : tecnicas) {
			if (t.getTipoTecnica() == tipo) {
				return t;
			}
		}
		return null;
	}

	// Devuelve la ofensiva con mas usos restantes, o null si no hay ninguna.
	// Con ">" (y no ">="), ante un empate se queda con la primera de la escuela.
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

/*
 * Criterio por defecto. Primero descarta las técnicas que no se
 * pueden usar (sin energía, sin usos, combatiente fuera de combate o bloqueadas por el
 * compañero). Luego elige, en este orden: una técnica de recuperación si al combatiente
 * le falta vida; una defensiva si no tiene una defensa activa; la ofensiva con más usos
 * restantes. Si ninguna es posible, devuelve vacío y se realiza un ataque básico. Los
 * empates se resuelven por el orden de la escuela: las técnicas se recorren en el orden
 * en que se aprendieron y solo se reemplaza la elegida si otra es estrictamente mejor.
 * Es determinista: ante el mismo estado siempre elige lo mismo.
 */