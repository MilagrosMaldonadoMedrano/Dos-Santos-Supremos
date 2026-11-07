package efectos;

import java.util.List;

import combatiente.Combatiente;

import java.util.ArrayList;

public class EfectoCompuesto implements Efecto{
	private List<Efecto> efectos;
	
	public EfectoCompuesto () {
		efectos = new ArrayList<Efecto>();
	}
	
	public void agregar(Efecto efecto) {
		efectos.add(efecto);
	}
	
	//composite
	
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente objetivo) {
		for (Efecto e : efectos) {
			e.aplicarEfecto(ejecutor, objetivo);
		}
	}
}
