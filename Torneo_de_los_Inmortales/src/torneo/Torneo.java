/*
* Cada pareja tiene identificador único, asignado al crearse; el de los combatientes lo asigna el creador, en orden de creación. Un combatiente no puede inscribirse dos veces ni una pareja registrarse dos veces; el intento debe rechazarse con una excepción.
* El torneo se debe jugar únicamente con 4 u 8 parejas.
* Los cruces de la primera ronda se arman por orden de inscripción (primera contra segunda, tercera contra cuarta, etc.). En las siguientes, por el orden de los cruces de la ronda anterior.
* Una pareja pierde cuando sus dos integrantes quedan fuera de combate. El torneo termina cuando queda una sola pareja.
* Las colecciones se eligen según la necesidad (conservar el orden de rondas y cruces, evitar duplicados, registrar cuántas veces cada combatiente usó cada técnica, ordenar la tabla de posiciones). 
*/
package torneo;
import combatiente.Combatiente;
import interesado.Notificador;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import combatiente.Combatiente;
import interesado.Interesado;
import interesado.Notificador;

import excepciones.InscripcionInvalidaException;
import excepciones.TorneoInvalidoException;


public class Torneo {
	
	private Set<Pareja> parejas;
	private Set<Combatiente> inscriptos;
	private List<Ronda> rondas;
	private Notificador notificador;
	private Pareja campeon;

    // HECHO
	public Torneo() {
		
		this.parejas = new LinkedHashSet<>();
		this.inscriptos = new HashSet<>();
		this.rondas = new LinkedList<>();
		this.notificador = new Notificador();
	}

    // HECHO
	public boolean parejaValida(Pareja p) {
		// Recorrer la lista de combatientes (son 2).
		for (Combatiente c : p.getCombatientes()) {
			// Si algún combatiente ya está entre los inscriptos,
			if (inscriptos.contains(c))
				// Se devuelve 'Rechazado'
				return false;
		}
		
		// Si llegó hasta aquí, es porque cumple con los requisitos.
		return true;
	}

    // HECHO
	// Rechaza (§5): pareja repetida o combatiente que ya está en otra pareja.
		// null = error de programación, por eso queda IllegalArgumentException (no verificada).
		public void inscribir(Pareja p) throws InscripcionInvalidaException {
			if (p == null)
				throw new IllegalArgumentException("La pareja no puede ser null");
			if (parejas.contains(p))
				throw new InscripcionInvalidaException("La pareja " + p.getId() + " ya está inscripta");
			for (Combatiente c : p.getCombatientes()) {
				if (inscriptos.contains(c))
					throw new InscripcionInvalidaException(c.getNombre() + " ya está inscripto en otra pareja");
			}
			parejas.add(p);
			inscriptos.addAll(p.getCombatientes());
		}

    // HECHO
		// Agrega un interesado (Heraldo, Cronista...) al notificador del torneo (Observer).
		public void suscribir(Interesado interesado) {
			notificador.suscribir(interesado);
		}
	/*public Pareja iniciar() {
		if (parejas.size() != 4 && parejas.size() != 8)
			throw new IllegalStateException("El torneo se juega con 4 u 8 parejas");
		// ... jugar las rondas
	}*/


	public Pareja iniciar() throws TorneoInvalidoException {
		if (parejas.size() != 4 && parejas.size() != 8)
			throw new TorneoInvalidoException("El torneo se juega con 4 u 8 parejas (hay " + parejas.size() + ")");
		// TODO: jugar las rondas hasta que quede una pareja (campeon)
		return campeon;
	}
    // HECHO
	public List<Ronda> getRondas() {
		return this.rondas;
	}

    // HECHO
	public Pareja getCampeon() {
		return this.campeon;
	}
}
