package efectos;

import java.util.List;

import combatiente.Combatiente;

import java.util.ArrayList;

public class EfectoCompuesto implements Efecto{
	private List<Efecto> efectos;
	
	public EfectoCompuesto () {
		efectos = new ArrayList<Efecto>(); //uso un array porque solo vamos a agregar.
	}
	
	public void agregar(Efecto efecto) {
		efectos.add(efecto);
	}
	
	//composite
	
	@Override
	public void aplicarEfecto(Combatiente afectado) {
		for (Efecto e : efectos) {
			e.aplicarEfecto(afectado);
		}
	}
}
