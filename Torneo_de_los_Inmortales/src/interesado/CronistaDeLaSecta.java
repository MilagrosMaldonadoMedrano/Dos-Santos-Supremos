package interesado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * OBSERVER: interesado que registra todos los acontecimientos en un historial y, con los
 * resultados, arma la tabla de posiciones. Las pruebas de combate y torneo verifican a
 * través de su historial, no leyendo la pantalla.
 * Lo crean: los main (App, App2 y App3) y las pruebas, que lo suscriben al torneo.
 */
public class CronistaDeLaSecta implements Interesado {
	private final List<Acontecimiento> historial = new ArrayList<>();      // en el orden en que ocurrieron
	private final Map<Integer, Posicion> posiciones = new HashMap<>();     // id de pareja y su posición

	/*
	 * Guarda el inicio del combate en el historial.
	 * Lo llama: InicioCombate.notificarA.
	 */
	@Override
	public void inicioCombate(InicioCombate acontecimiento) {
		historial.add(acontecimiento);
	}

	/*
	 * Guarda la acción en el historial.
	 * Lo llama: AccionRealizada.notificarA.
	 */
	@Override
	public void accionRealizada(AccionRealizada acontecimiento) {
		historial.add(acontecimiento);
	}

	/*
	 * Guarda la eliminación en el historial.
	 * Lo llama: Eliminacion.notificarA.
	 */
	@Override
	public void eliminacion(Eliminacion acontecimiento) {
		historial.add(acontecimiento);
	}

	/*
	 * Guarda el resultado y actualiza la posición de las dos parejas: queda su ÚLTIMA
	 * ronda jugada y la vida total con la que terminó.
	 * Lo llama: ResultadoCombate.notificarA.
	 */
	@Override
	public void resultadoCombate(ResultadoCombate r) {
		historial.add(r);
		posiciones.put(r.getIdParejaA(), new Posicion(r.getIdParejaA(), r.getIntegrantesA(), r.getRonda(), r.getVidaA()));
		posiciones.put(r.getIdParejaB(), new Posicion(r.getIdParejaB(), r.getIntegrantesB(), r.getRonda(), r.getVidaB()));
	}

	/*
	 * Guarda la proclamación en el historial.
	 * Lo llama: Proclamacion.notificarA.
	 */
	@Override
	public void proclamacion(Proclamacion acontecimiento) {
		historial.add(acontecimiento);
	}

	/*
	 * Devuelve el historial completo, en orden (copia de solo lectura).
	 * Lo usa: CronistaDeLaSectaTest (por ejemplo, para comparar dos torneos iguales).
	 */
	public List<Acontecimiento> getHistorial() {
		return List.copyOf(historial);
	}

	/*
	 * Devuelve la tabla de posiciones ordenada con Posicion.compareTo.
	 * Lo usan: los main (App, App2 y App3), que se la pasan al Heraldo, y las pruebas.
	 */
	public List<Posicion> tablaDePosiciones() {
		List<Posicion> tabla = new ArrayList<>(posiciones.values());
		Collections.sort(tabla);
		return tabla;
	}
}
