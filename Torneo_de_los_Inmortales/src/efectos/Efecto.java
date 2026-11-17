package efectos;

import combatiente.Combatiente;

public interface Efecto {
	
	/*hora el efecto recibe también al EJECUTOR (quien usa latecnica)
	como la consigna pide que una tecnica provoque distinto danio segun quien la 
	ejecuta se debe poder pasar el ejecutorde la tecnica pata preguntarle cuanto pega
	
	*/
	void aplicarEfecto(Combatiente ejecutor, Combatiente afectado);
	//public void aplicarEfecto(Combatiente afectado);
}
