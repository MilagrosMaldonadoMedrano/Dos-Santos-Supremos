package combatiente;

import java.util.Optional;
import java.util.Set;

import equipamiento_y_tecnicas.Tecnica;
import equipamiento_y_tecnicas.TipoTecnica;

/*
 * STRATEGY: criterio alternativo definido por el grupo, para mostrar que la estrategia
 * se puede cambiar. Ignora la recuperación y la defensa: entre las técnicas ofensivas
 * que puede usar (con energía, con usos y no bloqueadas por el compañero) elige la de
 * mayor costo de energía, considerada la más poderosa. En empate gana la primera en el
 * orden de la escuela. Si no hay ninguna posible, devuelve vacío y se hace un ataque básico.
 * Se asigna con combatiente.setCriterio(new CriterioAgresivo()), sin modificar Combatiente.
 */
public class CriterioAgresivo implements CriterioDeAccion {

	/*
	 * Elige la ofensiva posible de mayor costo, o vacío si no hay ninguna.
	 * Usa ">" para que, en un empate, quede la primera en el orden de la escuela.
	 * Lo usa: Combatiente.elegirTecnica, cuando este es su criterio.
	 */
	@Override
	public Optional<Tecnica> elegir(Combatiente c, Set<Tecnica> bloqueadas) {
		Tecnica mejor = null;
		for (Tecnica t : c.getTecnicas()) {
			if (t.getTipoTecnica() == TipoTecnica.OFENSIVA
					&& c.puedeUsar(t) && !bloqueadas.contains(t)
					&& (mejor == null || t.getCostoEnergia() > mejor.getCostoEnergia())) {
				mejor = t;
			}
		}
		return Optional.ofNullable(mejor);
	}
}
