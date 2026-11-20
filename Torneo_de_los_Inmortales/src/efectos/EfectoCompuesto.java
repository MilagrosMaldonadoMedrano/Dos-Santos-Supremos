package efectos;

import java.util.List;

import combatiente.Combatiente;

import java.util.ArrayList;

/*
 * COMPOSITE: efecto que agrupa otros efectos y los aplica uno por uno, en el orden en
 * que se agregaron. Permite armar técnicas que hacen varias cosas a la vez sin crear
 * clases nuevas de efecto.
 * Lo usan: DominioDeLaNada (daño, drenar energía y veneno) y MeditacionDelInmortal
 * (curación y regeneración).
 */
public class EfectoCompuesto implements Efecto{
	private List<Efecto> efectos;   // lista porque solo se agregan y se recorren en orden

	/*
	 * Crea el compuesto sin efectos; se agregan con agregar.
	 * Lo usan: DominioDeLaNada y MeditacionDelInmortal.
	 */
	public EfectoCompuesto () {
		efectos = new ArrayList<Efecto>();
	}

	/*
	 * Suma un efecto (simple o compuesto) al grupo.
	 * Lo usan: el crearEfectoCompuesto de DominioDeLaNada y de MeditacionDelInmortal.
	 */
	public void agregar(Efecto efecto) {
		efectos.add(efecto);
	}

	/*
	 * Aplica cada efecto del grupo, pasándoles el ejecutor y el afectado.
	 * Lo usa: Tecnica.realizar.
	 */
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		for (Efecto e : efectos) {
			e.aplicarEfecto(ejecutor, afectado);
		}
	}
}
