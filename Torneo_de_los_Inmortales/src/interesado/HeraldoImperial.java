package interesado;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

/*
 * OBSERVER: interesado que presenta los acontecimientos por pantalla. Es la ÚNICA clase
 * del sistema que escribe en pantalla, incluido lo que muestra el main.
 * ADAPTER: adapta los acontecimientos del torneo al PrintStream de Java (System.out).
 * Lo crea: el main (App), que lo suscribe al torneo.
 */
public class HeraldoImperial implements Interesado {
	private final PrintStream salida;   // donde escribe (System.out en el main)

	/*
	 * Guarda la salida donde va a escribir. Si es null, lanza NullPointerException.
	 * Lo usa: el main (App).
	 */
	public HeraldoImperial(PrintStream salida) {
		this.salida = Objects.requireNonNull(salida, "La salida no puede ser null");
	}

	/*
	 * Muestra el comienzo del combate, dejando una línea en blanco antes.
	 * Lo llama: InicioCombate.notificarA.
	 */
	@Override
	public void inicioCombate(InicioCombate acontecimiento) {
		salida.println();
		salida.println(acontecimiento.descripcion());
	}

	/*
	 * Muestra la acción realizada.
	 * Lo llama: AccionRealizada.notificarA.
	 */
	@Override
	public void accionRealizada(AccionRealizada acontecimiento) {
		salida.println(acontecimiento.descripcion());
	}

	/*
	 * Muestra que un combatiente quedó fuera de combate.
	 * Lo llama: Eliminacion.notificarA.
	 */
	@Override
	public void eliminacion(Eliminacion acontecimiento) {
		salida.println(acontecimiento.descripcion());
	}

	/*
	 * Muestra el resultado del combate.
	 * Lo llama: ResultadoCombate.notificarA.
	 */
	@Override
	public void resultadoCombate(ResultadoCombate acontecimiento) {
		salida.println(acontecimiento.descripcion());
	}

	/*
	 * Anuncia a los Dos Santos Supremos, dejando una línea en blanco antes.
	 * Lo llama: Proclamacion.notificarA.
	 */
	@Override
	public void proclamacion(Proclamacion acontecimiento) {
		salida.println();
		salida.println(acontecimiento.descripcion());
	}

	/*
	 * Muestra la tabla de posiciones numerada.
	 * Lo usa: el main (App), con la tabla que le pide al Cronista.
	 */
	public void presentarTabla(List<Posicion> tabla) {
		salida.println();
		salida.println("=== Tabla de posiciones ===");
		int lugar = 1;
		for (Posicion p : tabla) {
			salida.println(lugar + ". " + p);
			lugar++;
		}
	}

	/*
	 * Muestra un mensaje suelto, por ejemplo un error atrapado en un catch.
	 * Lo usa: el main (App).
	 */
	public void anunciar(String mensaje) {
		salida.println(mensaje);
	}
}
