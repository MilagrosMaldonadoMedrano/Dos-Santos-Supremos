package interesado;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

// Presenta los acontecimientos por pantalla. Es la ÚNICA clase que escribe en pantalla (§7).
// Adapter: adapta los acontecimientos del torneo al PrintStream (System.out).
public class HeraldoImperial implements Interesado {
	private final PrintStream salida;

	public HeraldoImperial(PrintStream salida) {
		this.salida = Objects.requireNonNull(salida, "La salida no puede ser null");
	}

	@Override
	public void inicioCombate(InicioCombate acontecimiento) {
		salida.println();
		salida.println(acontecimiento.descripcion());
	}

	@Override
	public void accionRealizada(AccionRealizada acontecimiento) {
		salida.println(acontecimiento.descripcion());
	}

	@Override
	public void eliminacion(Eliminacion acontecimiento) {
		salida.println(acontecimiento.descripcion());
	}

	@Override
	public void resultadoCombate(ResultadoCombate acontecimiento) {
		salida.println(acontecimiento.descripcion());
	}

	@Override
	public void proclamacion(Proclamacion acontecimiento) {
		salida.println();
		salida.println(acontecimiento.descripcion());
	}

	public void presentarTabla(List<Posicion> tabla) {
		salida.println();
		salida.println("=== Tabla de posiciones ===");
		int lugar = 1;
		for (Posicion p : tabla) {
			salida.println(lugar + ". " + p);
			lugar++;
		}
	}

	// Para lo que muestra el main (por ejemplo, errores atrapados en un catch).
	public void anunciar(String mensaje) {
		salida.println(mensaje);
	}
}