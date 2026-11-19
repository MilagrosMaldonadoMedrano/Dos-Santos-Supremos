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

// Una pareja no conoce a sus otras parejas.
// Implementa Iterable: un "for (Combatiente c : pareja)" recorre SOLO a los que
// están en pie, ordenados por id (lo hace IteradorEnPie) -> patrón Iterator.
public class Pareja implements Iterable<Combatiente> {
	private final int id;
	private final List<Combatiente> combatientes;
	// Regla del compañero (§6): qué integrante "tiene tomada" cada técnica en este combate.
	private final Map<Tecnica, Combatiente> tecnicasEnUso;

	public Pareja(int id, Combatiente a, Combatiente b) {
		if (a == null || b == null || a.equals(b)) {
			throw new IllegalArgumentException("La pareja debe tener dos combatientes distintos.");
		}
		this.id = id;
		this.combatientes = new ArrayList<>(List.of(a, b));
		// Ordenados por id: dentro de la pareja actúa primero el de menor id (§6).
		this.combatientes.sort(Comparator.comparingInt(Combatiente::getIdentificador));
		this.tecnicasEnUso = new HashMap<>();
	}

	@Override
	public Iterator<Combatiente> iterator() {
		return new IteradorEnPie(this.combatientes);
	}

	// Una pareja pierde cuando sus dos integrantes quedan fuera de combate (§5).
	public boolean tieneIntegrantesEnPie() {
		return iterator().hasNext();
	}

	public int getId() {
		return id;
	}

	// Copia: desde afuera no se puede modificar la lista de la pareja.
	public List<Combatiente> getCombatientes() {
		return List.copyOf(this.combatientes);
	}

	// Suma de la vida actual; un caído suma 0 (§6, desempate por límite de asaltos).
	public int vidaTotal() {
		int vidaTotal = 0;
		for (Combatiente c : combatientes) {
			vidaTotal += c.getVidaActual();
		}
		return vidaTotal;
	}

	// REGLA DEL COMPAÑERO (§6): devuelve las técnicas que c NO puede usar porque las tiene
	// tomadas su compañero, mientras el compañero siga en pie y le queden usos de ella.
	// Este conjunto se le pasa al criterio de acción: combatiente.elegirTecnica(bloqueadas).
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

	public boolean puedeUsar(Combatiente c, Tecnica t) {
		return !tecnicasBloqueadasPara(c).contains(t);
	}

	// Se llama después de que c usa la técnica t.
	// Si nadie la tenía tomada, o el que la tenía ya no bloquea (cayó o se quedó
	// sin usos), pasa a ser de c. Si la tiene c, queda igual.
	public void registrarUso(Combatiente c, Tecnica t) {
		validarIntegrante(c);
		Combatiente actual = tecnicasEnUso.get(t);
		if (actual == null || !bloquea(actual, t)) {
			tecnicasEnUso.put(t, c);
		}
	}

	// Un integrante bloquea una técnica si sigue en pie y le quedan usos de ella.
	private boolean bloquea(Combatiente quien, Tecnica t) {
		return quien.puedeActuar() && quien.usosRestantes(t) > 0;
	}

	// Al comenzar cada combate: la restricción del compañero se reinicia (§6)
	// y los dos vuelven a vida y energía máximas, pasando a EnPie.
	public void prepararParaCombate() {
		tecnicasEnUso.clear();
		for (Combatiente c : combatientes) {
			c.prepararParaCombate();
		}
	}

	// Al terminar el combate: se borra la restricción y los dos vuelven a Preparado.
	public void finalizarCombate() {
		tecnicasEnUso.clear();
		for (Combatiente c : combatientes) {
			c.finalizarCombate();
		}
	}

	public boolean contiene(Combatiente c) {
		return combatientes.contains(c);
	}

	private void validarIntegrante(Combatiente c) {
		if (!contiene(c)) {
			throw new IllegalArgumentException(c.getNombre() + " no pertenece a la pareja " + id);
		}
	}

	// Dos parejas son la misma si tienen el mismo id (evita registrarla dos veces).
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Pareja p = (Pareja) o;
		return this.id == p.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Pareja " + id + " (" + combatientes.get(0).getNombre() + " y " + combatientes.get(1).getNombre() + ")";
	}
}