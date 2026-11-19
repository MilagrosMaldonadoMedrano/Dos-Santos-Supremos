package interesado;

import java.util.ArrayList;
import java.util.List;

// Observer: el sujeto. Guarda la lista de interesados y les avisa los acontecimientos.
public class Notificador {
	private final List<Interesado> interesados = new ArrayList<>();

	public void suscribir(Interesado interesado) {
		if (interesado == null) {
			throw new IllegalArgumentException("El interesado no puede ser null");
		}
		interesados.add(interesado);
	}
}