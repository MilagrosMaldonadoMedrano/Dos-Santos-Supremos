package torneo;

import combatiente.Combatiente;
import interesado.Notificador;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import combatiente.Combatiente;

/*
 * ITERATOR: recorre los integrantes de una pareja salteando a los que están fuera de
 * combate. Así el combate escribe un for simple sin saber cómo está guardada la pareja.
 * Lo crea: Pareja.iterator.
 */
public class IteradorEnPie implements Iterator<Combatiente> {
	private final List<Combatiente> integrantes;
	private int posicion;   // índice del próximo integrante a revisar

	/*
	 * Recibe la lista de integrantes (ya ordenada por id) y arranca desde el primero.
	 * Lo usa: Pareja.iterator.
	 */
	public IteradorEnPie(List<Combatiente> integrantes) {
		this.integrantes = integrantes;
		this.posicion = 0;
	}

	/*
	 * Avanza salteando a los que no pueden actuar (caídos) e indica si queda alguno en pie.
	 * Lo usan: el for de Java sobre una Pareja, Pareja.tieneIntegrantesEnPie y next.
	 */
	@Override
	public boolean hasNext() {
		while (posicion < integrantes.size() && !integrantes.get(posicion).puedeActuar()) {
			posicion++;
		}
		return posicion < integrantes.size();
	}

	/*
	 * Devuelve el próximo integrante en pie. Si no queda ninguno, lanza
	 * NoSuchElementException.
	 * Lo usa: el for de Java sobre una Pareja.
	 */
	@Override
	public Combatiente next() {
		if (
			!hasNext()
		) {
			throw new NoSuchElementException("No hay más combatientes en pie en la pareja.");
		}
		return integrantes.get(posicion++);
	}
}
