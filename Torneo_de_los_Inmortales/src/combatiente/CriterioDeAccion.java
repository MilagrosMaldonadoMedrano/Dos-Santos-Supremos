package combatiente;

import java.util.Optional;
import java.util.Set;

import equipamiento_y_tecnicas.Tecnica;
//-------------------------Patron Strategy----------------------------
/*
 * . Representa la forma en que un combatiente decide qué acción
 * realizar en su turno. Combatiente tiene un CriterioDeAccion y le delega la decisión
 * con elegirTecnica(), sin saber qué criterio concreto tiene; así se pueden agregar
 * criterios nuevos o cambiar el de un combatiente (setCriterio) sin modificar
 * Combatiente. 
 * El método elegir recibe al combatiente que actúa, para consultar su
 * vida, energía, usos y defensa, y el conjunto de técnicas bloqueadas por la regla
 * del compañero, que calcula la Pareja. Devuelve un Optional con la técnica elegida,
 * o vacío si ninguna es posible, en cuyo caso el combate realiza un ataque básico.
 */
public interface CriterioDeAccion {

	
	Optional<Tecnica> elegir(Combatiente combatiente, Set<Tecnica> bloqueadas);
}