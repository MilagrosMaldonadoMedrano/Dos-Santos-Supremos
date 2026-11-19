package torneo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import combatiente.Combatiente;
import excepciones.InscripcionInvalidaException;
import excepciones.TorneoInvalidoException;
import interesado.Interesado;
import interesado.Notificador;
import interesado.Proclamacion;

public class Torneo {
	private final Set<Pareja> parejas;          // LinkedHashSet: sin repetidos y en orden de inscripción
	private final Set<Combatiente> inscriptos;  // HashSet: para rechazar combatientes repetidos
	private final List<Ronda> rondas;           // List: conserva el orden de las rondas
	private final Notificador notificador;
	private Pareja campeon;

	public Torneo() {
		this.parejas = new LinkedHashSet<>();
		this.inscriptos = new HashSet<>();
		this.rondas = new ArrayList<>();
		this.notificador = new Notificador();
	}

	public void inscribir(Pareja p) throws InscripcionInvalidaException {
		if (p == null)
			throw new IllegalArgumentException("La pareja no puede ser null");
		if (campeon != null)
			throw new InscripcionInvalidaException("El torneo ya se jugó");
		if (parejas.contains(p))
			throw new InscripcionInvalidaException("La pareja " + p.getId() + " ya está inscripta");
		for (Combatiente c : p.getCombatientes()) {
			if (inscriptos.contains(c))
				throw new InscripcionInvalidaException(c.getNombre() + " ya está inscripto en otra pareja");
		}
		parejas.add(p);
		inscriptos.addAll(p.getCombatientes());
	}

	// Observer: el Heraldo, el Cronista o cualquier interesado nuevo, sin tocar el torneo (§7).
	public void suscribir(Interesado interesado) {
		notificador.suscribir(interesado);
	}

	public Pareja iniciar() throws TorneoInvalidoException {
		if (campeon != null)
			throw new TorneoInvalidoException("El torneo ya se jugó");
		if (parejas.size() != 4 && parejas.size() != 8)
			throw new TorneoInvalidoException("El torneo se juega con 4 u 8 parejas (hay " + parejas.size() + ")");

		// Primera ronda: en orden de inscripción. Las siguientes: con las ganadoras, en orden.
		List<Pareja> enJuego = new ArrayList<>(parejas);
		int numero = 0;
		while (enJuego.size() > 1) {   // termina cuando queda una sola pareja (§5)
			numero++;
			Ronda ronda = new Ronda(numero, enJuego);
			rondas.add(ronda);
			enJuego = ronda.jugar(notificador);
		}
		campeon = enJuego.get(0);
		notificador.publicar(new Proclamacion(numero, campeon));
		return campeon;
	}

	public List<Pareja> getParejas() {
		return List.copyOf(parejas);
	}

	public List<Ronda> getRondas() {
		return List.copyOf(rondas);
	}

	public Pareja getCampeon() {
		return campeon;
	}
}