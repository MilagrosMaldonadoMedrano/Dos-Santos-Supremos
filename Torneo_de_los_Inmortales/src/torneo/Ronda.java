package torneo;

import java.util.ArrayList;
import java.util.List;

import interesado.Notificador;

/*
 * Etapa del torneo: arma los cruces de a dos parejas y los juega en orden; las
 * ganadoras pasan a la ronda siguiente.
 * La crea: Torneo.iniciar, una por cada ronda.
 */
public class Ronda {
	private final int numero;
	private final List<Cruce> cruces;   // lista: conserva el orden de los cruces

	/*
	 * Arma los cruces emparejando en orden: la primera contra la segunda, la tercera
	 * contra la cuarta, etc. Si la cantidad de parejas no es par, lanza
	 * IllegalArgumentException.
	 * Lo usa: Torneo.iniciar.
	 */
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

	/*
	 * Resuelve cada cruce y devuelve las ganadoras EN EL ORDEN de los cruces: así la
	 * ronda siguiente se arma por el orden de los cruces de la ronda anterior.
	 * Lo usa: Torneo.iniciar.
	 */
	public List<Pareja> jugar(Notificador notificador) {
		List<Pareja> ganadoras = new ArrayList<>();
		for (Cruce cruce : cruces) {
			ganadoras.add(cruce.resolver(numero, notificador));
		}
		return ganadoras;
	}

	/*
	 * Devuelve el número de la ronda (1, 2, 3...).
	 * Hoy no lo usa ninguna clase; queda para consultar la ronda.
	 */
	public int getNumero() {
		return numero;
	}

	/*
	 * Devuelve los cruces en una copia que no se puede modificar.
	 * Hoy no lo usa ninguna clase; sirve para verificar el orden de los cruces.
	 */
	public List<Cruce> getCruces() {
		return List.copyOf(cruces);
	}
}
