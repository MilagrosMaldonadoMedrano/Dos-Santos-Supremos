package torneo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import combatiente.Combatiente;
import equipamiento_y_tecnicas.Tecnica;

/*
 * Unidad que participa en el torneo: dos combatientes con un identificador.
 * ITERATOR: implementa Iterable, así un "for (Combatiente c : pareja)" recorre solo a
 * los integrantes en pie, ordenados por id (lo hace IteradorEnPie).
 * También lleva la regla del compañero: qué integrante tiene tomada cada técnica.
 * Una pareja no conoce a las otras parejas.
 * La crean: los main (App, App2 y App3) y las pruebas (ParejaTest, CombateTest...).
 */
public class Pareja implements Iterable<Combatiente> {
	private final int id;
	private final List<Combatiente> combatientes;          // ordenados por id
	private final Map<Tecnica, Combatiente> tecnicasEnUso;  // regla del compañero: técnica y quién la tiene tomada

	/*
	 * Crea la pareja con su id y sus dos combatientes, ordenados por id (dentro de la
	 * pareja actúa primero el de menor id). Si alguno es null o son el mismo, lanza
	 * IllegalArgumentException.
	 * Lo usan: los main (App, App2 y App3) y las pruebas.
	 */
	public Pareja(int id, Combatiente a, Combatiente b) {
		if (a == null || b == null || a.equals(b)) {
			throw new IllegalArgumentException("La pareja debe tener dos combatientes distintos.");
		}
		this.id = id;
		this.combatientes = new ArrayList<>(List.of(a, b));
		this.combatientes.sort(Comparator.comparingInt(Combatiente::getIdentificador));
		this.tecnicasEnUso = new HashMap<>();
	}

	/*
	 * ITERATOR: devuelve un iterador que recorre solo a los integrantes en pie.
	 * Lo usan: todos los "for (Combatiente c : pareja)" de Combate y tieneIntegrantesEnPie.
	 */
	@Override
	public Iterator<Combatiente> iterator() {
		return new IteradorEnPie(this.combatientes);
	}

	/*
	 * Indica si le queda al menos un integrante en pie. Una pareja pierde cuando sus
	 * dos integrantes quedan fuera de combate.
	 * Lo usa: Combate (terminado y para saber quién ganó).
	 */
	public boolean tieneIntegrantesEnPie() {
		return iterator().hasNext();
	}

	/*
	 * Devuelve el identificador de la pareja.
	 * Lo usan: Combate (empieza la de menor id), Torneo (mensajes) y los acontecimientos.
	 */
	public int getId() {
		return id;
	}

	/*
	 * Devuelve los dos integrantes en una copia: desde afuera no se puede modificar
	 * la lista de la pareja.
	 * Lo usan: Torneo.inscribir (rechaza combatientes repetidos) y Acontecimiento.integrantes.
	 */
	public List<Combatiente> getCombatientes() {
		return List.copyOf(this.combatientes);
	}

	/*
	 * Suma la vida actual de los dos integrantes; un caído suma 0.
	 * Lo usan: Combate.ganadorPorVida (desempate al llegar al límite de asaltos)
	 * ResultadoCombate (vida total de cada pareja al terminar) y AcontecimientoTest.
	 */
	public int vidaTotal() {
		int vidaTotal = 0;
		for (Combatiente c : combatientes) {
			vidaTotal += c.getVidaActual();
		}
		return vidaTotal;
	}

	/*
	 * REGLA DEL COMPAÑERO: devuelve las técnicas que c NO puede usar porque las tiene
	 * tomadas su compañero, mientras el compañero siga en pie y le queden usos de ella.
	 * Lo usan: Combate.actuar, que le pasa el resultado a combatiente.elegirTecnica,
	 * y ParejaTest.
	 */
	public Set<Tecnica> tecnicasBloqueadasPara(Combatiente c) {
		validarIntegrante(c);
		Set<Tecnica> bloqueadas = new HashSet<>();
		for (Map.Entry<Tecnica, Combatiente> uso : tecnicasEnUso.entrySet()) {
			if (bloquea(uso.getValue(), uso.getKey()) && !uso.getValue().equals(c)) {
				bloqueadas.add(uso.getKey());
			}
		}
		return bloqueadas;
	}

	/*
	 * REGLA DEL COMPAÑERO: indica si c puede usar una técnica puntual (si no está bloqueada).
	 * Lo usa: ParejaTest. El combate no lo necesita: trabaja con tecnicasBloqueadasPara.
	 */
	public boolean puedeUsar(Combatiente c, Tecnica t) {
		return !tecnicasBloqueadasPara(c).contains(t);
	}

	/*
	 * REGLA DEL COMPAÑERO: registra que c usó la técnica t. Si nadie la tenía tomada, o el
	 * que la tenía ya no la bloquea (cayó o se quedó sin usos), pasa a ser de c.
	 * Lo usan: Combate.actuar, después de que la técnica se usó con éxito, y ParejaTest.
	 */
	public void registrarUso(Combatiente c, Tecnica t) {
		validarIntegrante(c);
		Combatiente actual = tecnicasEnUso.get(t);
		if (actual == null || !bloquea(actual, t)) {
			tecnicasEnUso.put(t, c);
		}
	}

	/*
	 * Un integrante bloquea una técnica si sigue en pie y le quedan usos de ella.
	 * Lo usan: tecnicasBloqueadasPara y registrarUso.
	 */
	private boolean bloquea(Combatiente quien, Tecnica t) {
		return quien.puedeActuar() && quien.usosRestantes(t) > 0;
	}

	/*
	 * Al comenzar cada combate: reinicia la regla del compañero y prepara a los dos
	 * integrantes (vida y energía al máximo, estado EnPie).
	 * Lo usan: Combate.jugar y ParejaTest.
	 */
	public void prepararParaCombate() {
		tecnicasEnUso.clear();
		for (Combatiente c : combatientes) {
			c.prepararParaCombate();
		}
	}

	/*
	 * Al terminar el combate: borra la regla del compañero y los dos integrantes vuelven
	 * a Preparado.
	 * Lo usan: Combate.jugar y ParejaTest.
	 */
	public void finalizarCombate() {
		tecnicasEnUso.clear();
		for (Combatiente c : combatientes) {
			c.finalizarCombate();
		}
	}

	/*
	 * Indica si el combatiente pertenece a esta pareja.
	 * Lo usa: validarIntegrante.
	 */
	public boolean contiene(Combatiente c) {
		return combatientes.contains(c);
	}

	/*
	 * Si el combatiente no es de esta pareja, lanza IllegalArgumentException.
	 * Lo usan: tecnicasBloqueadasPara y registrarUso.
	 */
	private void validarIntegrante(Combatiente c) {
		if (!contiene(c)) {
			throw new IllegalArgumentException(c.getNombre() + " no pertenece a la pareja " + id);
		}
	}

	/*
	 * Dos parejas son la misma si tienen el mismo id.
	 * Lo usan: el LinkedHashSet de Torneo (rechaza una pareja repetida) y el constructor
	 * de Combate (una pareja no puede combatir contra sí misma).
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Pareja p = (Pareja) o;
		return this.id == p.id;
	}

	/*
	 * Hash por id, igual que equals (necesario para el LinkedHashSet de Torneo).
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/*
	 * Texto de la pareja: id y nombres de sus integrantes.
	 */
	@Override
	public String toString() {
		return "Pareja " + id + " (" + combatientes.get(0).getNombre() + " y " + combatientes.get(1).getNombre() + ")";
	}
}
