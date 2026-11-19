package torneo;

import java.util.ArrayList;
import java.util.List;

import interesado.Notificador;

public class Ronda {
	private final int numero;
	private final List<Cruce> cruces;   // List: conserva el orden de los cruces (§5)

	// §5: se emparejan en orden: 1ª vs 2ª, 3ª vs 4ª, etc.
	public Ronda(int numero, List<Pareja> parejas) {
		if (parejas.size() < 2 || parejas.size() % 2 != 0) {
			throw new IllegalArgumentException("Una ronda necesita una cantidad par de parejas");
		}
		this.numero = numero;
		this.cruces = new ArrayList<>();
		for (int i = 0; i < parejas.size(); i += 2) {
			cruces.add(new Cruce(parejas.get(i), parejas.get(i + 1)));
		}
	}

	// Devuelve las ganadoras EN EL ORDEN de los cruces: así la ronda siguiente
	// se arma "por el orden de los cruces de la ronda anterior" (§5).
	public List<Pareja> jugar(Notificador notificador) {
		List<Pareja> ganadoras = new ArrayList<>();
		for (Cruce cruce : cruces) {
			ganadoras.add(cruce.resolver(numero, notificador));
		}
		return ganadoras;
	}

	public int getNumero() {
		return numero;
	}

	public List<Cruce> getCruces() {
		return List.copyOf(cruces);
	}
}