package torneo;

import combatiente.Combatiente;
import interesado.Notificador;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import combatiente.Combatiente;

public class IteradorEnPie implements Iterator<Combatiente> {
    private final List<Combatiente> integrantes;
    private int posicion;

    public IteradorEnPie(List<Combatiente> integrantes) {
        this.integrantes = integrantes;
        this.posicion = 0;
    }

    @Override
    public boolean hasNext() {
        // Avanza el índice saltándose a los que ya cayeron (no están en pie)
        while (posicion < integrantes.size() && !integrantes.get(posicion).puedeActuar()) {
            posicion++;
        }
        return posicion < integrantes.size();
    }

    @Override
    public Combatiente next() {
        if (
        	// si no tiene siguiente
        	!hasNext()
        ) {
        	// Devuelve una excepción.
            throw new NoSuchElementException("No hay más combatientes en pie en la pareja.");
        }
        return integrantes.get(posicion++);
    }
}