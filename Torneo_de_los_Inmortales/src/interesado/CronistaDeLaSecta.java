package interesado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Registra los acontecimientos y arma la tabla de posiciones (§7).
// Las pruebas de combate y torneo verifican a través de su historial, no de la pantalla (§8).
public class CronistaDeLaSecta implements Interesado {
	private final List<Acontecimiento> historial = new ArrayList<>();
	private final Map<Integer, Posicion> posiciones = new HashMap<>();   // id de pareja -> su posición

	@Override
	public void inicioCombate(InicioCombate acontecimiento) {
		historial.add(acontecimiento);
	}

	@Override
	public void accionRealizada(AccionRealizada acontecimiento) {
		historial.add(acontecimiento);
	}

	@Override
	public void eliminacion(Eliminacion acontecimiento) {
		historial.add(acontecimiento);
	}

	// Cada resultado actualiza la posición de las dos parejas: queda su ÚLTIMA ronda y su vida final.
	@Override
	public void resultadoCombate(ResultadoCombate r) {
		historial.add(r);
		posiciones.put(r.getIdParejaA(), new Posicion(r.getIdParejaA(), r.getIntegrantesA(), r.getRonda(), r.getVidaA()));
		posiciones.put(r.getIdParejaB(), new Posicion(r.getIdParejaB(), r.getIntegrantesB(), r.getRonda(), r.getVidaB()));
	}

	@Override
	public void proclamacion(Proclamacion acontecimiento) {
		historial.add(acontecimiento);
	}

	public List<Acontecimiento> getHistorial() {
		return List.copyOf(historial);
	}

	public List<Posicion> tablaDePosiciones() {
		List<Posicion> tabla = new ArrayList<>(posiciones.values());
		Collections.sort(tabla);   // usa Posicion.compareTo
		return tabla;
	}
}