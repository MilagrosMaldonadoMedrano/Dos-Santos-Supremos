package efectos;

import combatiente.Combatiente;

public interface Efecto {
	public void aplicarEfecto(Combatiente ejecutor, Combatiente objetivo);
}
