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

/*
 * Torneo eliminatorio: inscribe parejas, las hace pelear en rondas hasta que queda una
 * sola y la proclama campeona. Se juega solo con 4 u 8 parejas.
 * OBSERVER: tiene un Notificador al que se suscriben los interesados (Heraldo, Cronista).
 * Lo crean: el main (App) y las pruebas.
 */
public class Torneo {
	private final Set<Pareja> parejas;          // LinkedHashSet: sin repetidos y en orden de inscripción
	private final Set<Combatiente> inscriptos;  // HashSet: para rechazar combatientes repetidos
	private final List<Ronda> rondas;           // lista: conserva el orden de las rondas
	private final Notificador notificador;
	private Pareja campeon;

	/*
	 * Crea el torneo vacío, con su notificador.
	 * Lo usan: el main (App) y las pruebas.
	 */
	public Torneo() {
		this.parejas = new LinkedHashSet<>();
		this.inscriptos = new HashSet<>();
		this.rondas = new ArrayList<>();
		this.notificador = new Notificador();
	}

	/*
	 * Inscribe una pareja. Rechaza con InscripcionInvalidaException una pareja ya
	 * inscripta, un combatiente que ya está en otra pareja, o si el torneo ya se jugó.
	 * Una pareja null es un error de programación (IllegalArgumentException).
	 * Lo usan: el main (App) y las pruebas.
	 */
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

	/*
	 * OBSERVER: suscribe un interesado (Heraldo, Cronista o uno nuevo) sin modificar
	 * la lógica del torneo.
	 * Lo usan: el main (App) y las pruebas.
	 */
	public void suscribir(Interesado interesado) {
		notificador.suscribir(interesado);
	}

	/*
	 * Juega el torneo completo y devuelve la campeona. Valida que haya 4 u 8 parejas y
	 * que no se haya jugado; si no, lanza TorneoInvalidoException. La primera ronda va en
	 * orden de inscripción y las siguientes con las ganadoras en orden, hasta que queda
	 * una sola pareja. Al final avisa la proclamación.
	 * Lo usan: el main (App) y las pruebas.
	 */
	public Pareja iniciar() throws TorneoInvalidoException {
		if (campeon != null)
			throw new TorneoInvalidoException("El torneo ya se jugó");
		if (parejas.size() != 4 && parejas.size() != 8)
			throw new TorneoInvalidoException("El torneo se juega con 4 u 8 parejas (hay " + parejas.size() + ")");

		List<Pareja> enJuego = new ArrayList<>(parejas);
		int numero = 0;
		while (enJuego.size() > 1) {
			numero++;
			Ronda ronda = new Ronda(numero, enJuego);
			rondas.add(ronda);
			enJuego = ronda.jugar(notificador);
		}
		campeon = enJuego.get(0);
		notificador.publicar(new Proclamacion(numero, campeon));
		return campeon;
	}

	/*
	 * Devuelve las parejas inscriptas, en orden de inscripción (copia de solo lectura).
	 * Hoy no lo usa ninguna clase; queda para consultar las inscripciones.
	 */
	public List<Pareja> getParejas() {
		return List.copyOf(parejas);
	}

	/*
	 * Devuelve las rondas jugadas, en orden (copia de solo lectura).
	 * Hoy no lo usa ninguna clase; queda para consultar las rondas.
	 */
	public List<Ronda> getRondas() {
		return List.copyOf(rondas);
	}

	/*
	 * Devuelve la pareja campeona, o null si el torneo todavía no se jugó.
	 * Lo usa: CronistaDeLaSectaTest.
	 */
	public Pareja getCampeon() {
		return campeon;
	}
}
