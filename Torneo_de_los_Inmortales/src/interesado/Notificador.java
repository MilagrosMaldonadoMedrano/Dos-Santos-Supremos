package interesado;

import java.util.ArrayList;
import java.util.List;

// Observer: el sujeto. Guarda los interesados y les avisa cada acontecimiento.
public class Notificador {
	private final List<Interesado> interesados = new ArrayList<>();

	public void suscribir(Interesado interesado) {
		if (interesado == null) {
			throw new IllegalArgumentException("El interesado no puede ser null");
		}
		interesados.add(interesado);
	}

	// El acontecimiento sabe a qué método del interesado llamar (notificarA):
	// así el Notificador no necesita preguntar de qué tipo es.
	public void publicar(Acontecimiento acontecimiento) {
		for (Interesado interesado : interesados) {
			acontecimiento.notificarA(interesado);
		}
	}
}