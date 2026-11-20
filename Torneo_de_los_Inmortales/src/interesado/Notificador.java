package interesado;

import java.util.ArrayList;
import java.util.List;

/*
 * OBSERVER: el sujeto. Guarda la lista de interesados y les avisa cada acontecimiento.
 * Lo crean: Torneo (uno por torneo), que se lo pasa a cada Ronda, Cruce y Combate.
 */
public class Notificador {
	private final List<Interesado> interesados = new ArrayList<>();

	/*
	 * Suma un interesado a la lista. Si es null, lanza IllegalArgumentException.
	 * Lo usa: Torneo.suscribir.
	 */
	public void suscribir(Interesado interesado) {
		if (interesado == null) {
			throw new IllegalArgumentException("El interesado no puede ser null");
		}
		interesados.add(interesado);
	}

	/*
	 * Avisa un acontecimiento a todos los interesados. El acontecimiento sabe a qué
	 * método del interesado llamar (notificarA), así el Notificador no pregunta su tipo.
	 * Lo usan: Combate (inicio, acción, eliminación, resultado) y Torneo (proclamación).
	 */
	public void publicar(Acontecimiento acontecimiento) {
		for (Interesado interesado : interesados) {
			acontecimiento.notificarA(interesado);
		}
	}
}
