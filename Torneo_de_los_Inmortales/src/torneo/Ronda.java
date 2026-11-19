package torneo;

import java.util.ArrayList;
import java.util.List;
import combatiente.Combatiente;
import interesado.Notificador;
public class Ronda {
	private int numero;
	private List<Cruce> cruces;
	
	// Recibe el número de ronda, y la lista de parejas.
	public Ronda(int numero, List<Pareja> pareja) {
		this.numero = numero;
		this.cruces = new ArrayList<>();
		
		
	}
	
	/*public List<Pareja> jugar(Notificador n) {}
	
	public int getNumero() {}
	
	public List<Cruce> getCruces() {}*/
}
