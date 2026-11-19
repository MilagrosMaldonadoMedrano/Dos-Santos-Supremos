package torneo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import combatiente.Combatiente;
import equipamiento_y_tecnicas.Tecnica;
import combatiente.Combatiente;
import interesado.Notificador;

// Una pareja no conoce a sus otras parejas.
public class Pareja implements Iterable<Combatiente> {
    private final int id;
    private final List<Combatiente> combatientes;
    private final Map<Tecnica, Combatiente> tecnicasEnUso;

    // HECHO
    public Pareja(int id, Combatiente a, Combatiente b) {
        if (
        	// si alguno de los dos es nulo
        	a == null || b == null 
        	// o si A es igual a B
        	|| a.equals(b)) 
        {
        	// Devuelve una excepción
            throw new IllegalArgumentException("La pareja debe tener dos combatientes distintos.");
        }
        
        this.id = id;
        this.combatientes = new ArrayList<>(List.of(a, b));
        this.combatientes.sort(Comparator.comparingInt(Combatiente::getIdentificador));
        this.tecnicasEnUso = new HashMap<>();
    }

    // HECHO
    @Override
    public Iterator<Combatiente> iterator() {
        return new IteradorEnPie(this.combatientes);
    }

    // HECHO
    public boolean tieneIntegrantesEnPie() {
        return iterator().hasNext();
    }

	// El combate necesita el id para que juegue primero la pareja de menor id,
	// y el torneo lo usa en los mensajes de error.
	public int getId() {
		return id;
	}

	// Devuelve una copia: desde afuera no se puede modificar la lista de la pareja.
	public List<Combatiente> getCombatientes() {
		return List.copyOf(this.combatientes);
	}

    // HECHO
	public int vidaTotal() {
		int vidaTotal = 0;
		for (Combatiente c : combatientes) 
			vidaTotal += c.getVidaActual();
		
		return vidaTotal;
	}
	
	//public boolean puedeUsar(Combatiente c, Tecnica t) {}
	
	public void registrarUso(Combatiente c, Tecnica t) {}
	
	public void prepararParaCombate() {}

    // HECHO
	public boolean contiene(Combatiente c) {
		return combatientes.contains(c);
	}

    // HECHO
	public boolean equals(Object o) {
		if (this == o) 
			return true;
    	if (o == null || getClass() != o.getClass())
    		return false;
		
		Pareja p = (Pareja) o;
		return this.id == p.id;
	}

    // HECHO
	public int hashCode() {
		return Objects.hash(id);
	}
}
