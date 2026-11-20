package combatiente;

import java.util.Optional;
import java.util.Set;

import equipamiento_y_tecnicas.Tecnica;

/*
 * STRATEGY: representa la forma en que un combatiente decide qué acción realizar en su
 * turno. Combatiente tiene un CriterioDeAccion y le delega la decisión con elegirTecnica(),
 * sin saber qué criterio concreto tiene; así se pueden agregar criterios nuevos o cambiar
 * el de un combatiente (setCriterio) sin modificar Combatiente.
 * Implementaciones: CriterioPorDefecto (el de la consigna) y CriterioAgresivo.
 */
public interface CriterioDeAccion {

	/*
	 * Recibe al combatiente que actúa, para consultar su vida, energía, usos y defensa,
	 * y las técnicas bloqueadas por la regla del compañero (las calcula Pareja).
	 * Devuelve un Optional con la técnica elegida, o vacío si ninguna es posible:
	 * en ese caso el combate hace un ataque básico.
	 * Lo usa: Combatiente.elegirTecnica.
	 */
	Optional<Tecnica> elegir(Combatiente combatiente, Set<Tecnica> bloqueadas);
}
