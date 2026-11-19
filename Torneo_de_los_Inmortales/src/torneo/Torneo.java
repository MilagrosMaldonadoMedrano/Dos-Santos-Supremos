/*
* Cada pareja tiene identificador único, asignado al crearse; el de los combatientes lo asigna el creador, en orden de creación. Un combatiente no puede inscribirse dos veces ni una pareja registrarse dos veces; el intento debe rechazarse con una excepción.
* El torneo se debe jugar únicamente con 4 u 8 parejas.
* Los cruces de la primera ronda se arman por orden de inscripción (primera contra segunda, tercera contra cuarta, etc.). En las siguientes, por el orden de los cruces de la ronda anterior.
* Una pareja pierde cuando sus dos integrantes quedan fuera de combate. El torneo termina cuando queda una sola pareja.
* Las colecciones se eligen según la necesidad (conservar el orden de rondas y cruces, evitar duplicados, registrar cuántas veces cada combatiente usó cada técnica, ordenar la tabla de posiciones). 
*/
package torneo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import combatiente.Combatiente;
import interesado.Interesado;
import interesado.Notificador;

public class Torneo {
	private int contParejas;
	private int cantMaxParejas;
	private Set<Pareja> parejas;
	private Set<Combatiente> inscriptos;
	private List<Ronda> rondas;
	private Notificador notificador;
	private Pareja campeon;

    // HECHO
	public Torneo() {
		this.contParejas = 0;
		this.parejas = new LinkedHashSet<>();
		this.inscriptos = new HashSet<>();
		this.rondas = new LinkedList<>();
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
	public void inscribir(Pareja p) throws Exception {
		// Si
		if (
			// Se alcanzó el límite de parejas para el torneo
			contParejas == cantMaxParejas ||
			// La pareja ya está en el conjunto
			parejas.contains(p) ||
			// O la pareja entrante no cumple los requisitos
			!parejaValida(p) ||
			// O la pareja es nula
			p == null
		)
			// Devuelve una excepción
			throw new Exception ("No se pudo inscribir la pareja.");
		
		// Si no hubo inconvenientes, se agrega la pareja al conjunto
		parejas.add(p);
		contParejas++;
	}

    // HECHO
	public void suscribir(Interesado n) throws Exception {
		if (
			// Si ya había un notificador
			this.notificador != null ||
			// O el notificador es nulo
			n == null
		) {
			throw new Exception("Ya hay un notificador asignado.");
		}
	}
	
	public Pareja iniciar() {
		
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
