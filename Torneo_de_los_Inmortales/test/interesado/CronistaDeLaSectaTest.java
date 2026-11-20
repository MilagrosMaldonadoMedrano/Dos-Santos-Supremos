package interesado;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import combatiente.CreadorCombatiente;
import equipamiento_y_tecnicas.AnilloEspiritual;
import equipamiento_y_tecnicas.ArmaduraDorada;
import equipamiento_y_tecnicas.CorteDeLosMilAmaneceres;
import equipamiento_y_tecnicas.DominioDeLaNada;
import equipamiento_y_tecnicas.EspadaCelestial;
import equipamiento_y_tecnicas.MeditacionDelInmortal;
import equipamiento_y_tecnicas.MuroDelCuerpoDorado;
import equipamiento_y_tecnicas.RugidoDeLaBestiaPrimordial;
import equipamiento_y_tecnicas.SelloDelFenix;
import escuela.Escuela;
import excepciones.TorneoException;
import torneo.Pareja;
import torneo.Torneo;

class CronistaDeLaSectaTest {

	// Arma un torneo con la cantidad de parejas pedida , con el Cronista suscripto.
	// Cada llamada crea su propio creador y combatientes nuevos: los usos de las tecnicas no se reinician entre torneos
	static Torneo armarTorneo(int cantidadParejas, CronistaDeLaSecta cronista) throws TorneoException {
		Escuela escuelaDeLaEspada = new Escuela("Escuela de la Espada Celestial",
				List.of(new CorteDeLosMilAmaneceres(), new RugidoDeLaBestiaPrimordial()),
				List.of(new EspadaCelestial()));
		Escuela palacioDelVacio = new Escuela("Palacio del Vacío",
				List.of(new DominioDeLaNada(), new MeditacionDelInmortal()),
				List.of(new AnilloEspiritual()));
		Escuela monasterio = new Escuela("Monasterio del Cuerpo Dorado",
				List.of(new MuroDelCuerpoDorado(), new MeditacionDelInmortal()),
				List.of(new ArmaduraDorada(), new SelloDelFenix()));

		CreadorCombatiente creador = new CreadorCombatiente();
		Torneo torneo = new Torneo();
		torneo.suscribir(cronista);

		String[] tipos = { CreadorCombatiente.CULTIVADOR, CreadorCombatiente.HECHICERO, CreadorCombatiente.MONJE };
		Escuela[] escuelas = { escuelaDeLaEspada, palacioDelVacio, monasterio };

		for (int i = 0; i < cantidadParejas; i++) {
			torneo.inscribir(new Pareja(i + 1,
					creador.crear(tipos[(2 * i) % 3], "Combatiente " + (2 * i + 1), escuelas[(2 * i) % 3]),
					creador.crear(tipos[(2 * i + 1) % 3], "Combatiente " + (2 * i + 2), escuelas[(2 * i + 1) % 3])));
		}
		return torneo;
	}

	@Test
	void laTablaDePosicionesQuedaOrdenadaPorUltimaRondaJugadaYVidaTotalFinal() throws TorneoException {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = armarTorneo(4, cronista);

		torneo.iniciar();
		List<Posicion> tabla = cronista.tablaDePosiciones();

		assertEquals(4, tabla.size());
		assertEquals(torneo.getCampeon().getId(), tabla.get(0).getIdPareja());

		// la final se juega en la ronda 2 y las eliminadas en la primera ronda quedan abajo
		assertEquals(2, tabla.get(0).getUltimaRonda());
		assertEquals(2, tabla.get(1).getUltimaRonda());
		assertEquals(1, tabla.get(2).getUltimaRonda());
		assertEquals(1, tabla.get(3).getUltimaRonda());

		// dentro de la misma ronda, ordenadas por vida total final de mayor a menor
		assertTrue(tabla.get(0).getVidaFinal() >= tabla.get(1).getVidaFinal());
		assertTrue(tabla.get(2).getVidaFinal() >= tabla.get(3).getVidaFinal());
	}

	@Test
	void conOchoParejasLaTablaRespetaLasTresRondas() throws TorneoException {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = armarTorneo(8, cronista);

		torneo.iniciar();
		List<Posicion> tabla = cronista.tablaDePosiciones();

		assertEquals(8, tabla.size());
		assertEquals(torneo.getCampeon().getId(), tabla.get(0).getIdPareja());

		int[] rondasEsperadas = { 3, 3, 2, 2, 1, 1, 1, 1 };
		for (int i = 0; i < tabla.size(); i++) {
			assertEquals(rondasEsperadas[i], tabla.get(i).getUltimaRonda());
		}
	}

	@Test
	void elHistorialRegistraLosAcontecimientosEnElOrdenEnQueOcurrieron() throws TorneoException {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = armarTorneo(4, cronista);

		torneo.iniciar();
		List<Acontecimiento> historial = cronista.getHistorial();

		assertInstanceOf(InicioCombate.class, historial.get(0));
		assertInstanceOf(Proclamacion.class, historial.get(historial.size() - 1));

		int inicios = 0;
		int resultados = 0;
		int proclamaciones = 0;
		for (Acontecimiento acontecimiento : historial) {
			if (acontecimiento instanceof InicioCombate) inicios++;
			if (acontecimiento instanceof ResultadoCombate) resultados++;
			if (acontecimiento instanceof Proclamacion) proclamaciones++;
		}
		// 4 parejas -> 3 combates
		assertEquals(3, inicios);
		assertEquals(3, resultados);
		assertEquals(1, proclamaciones);

		Proclamacion proclamacion = (Proclamacion) historial.get(historial.size() - 1);
		assertEquals(torneo.getCampeon().getId(), proclamacion.getIdPareja());
	}

	@Test
	void cadaResultadoActualizaLaPosicionConLaUltimaRondaJugada() throws TorneoException {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = armarTorneo(4, cronista);

		torneo.iniciar();

		ResultadoCombate finalDelTorneo = null;
		for (Acontecimiento acontecimiento : cronista.getHistorial()) {
			if (acontecimiento instanceof ResultadoCombate resultado) {
				finalDelTorneo = resultado;
			}
		}
		assertNotNull(finalDelTorneo);
		assertEquals(2, finalDelTorneo.getRonda());

		// la campeona jugo dos combates, pero en la tabla figura una sola vez y con los datos de la final
		Posicion primera = cronista.tablaDePosiciones().get(0);
		assertEquals(finalDelTorneo.getIdGanadora(), primera.getIdPareja());
		int vidaDeLaGanadora = finalDelTorneo.getIdGanadora() == finalDelTorneo.getIdParejaA()
				? finalDelTorneo.getVidaA()
				: finalDelTorneo.getVidaB();
		assertEquals(vidaDeLaGanadora, primera.getVidaFinal());
	}

	@Test
	void elHistorialNoSePuedeModificarDesdeAfuera() throws TorneoException {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();
		Torneo torneo = armarTorneo(4, cronista);

		torneo.iniciar();
		List<Acontecimiento> historial = cronista.getHistorial();
		int cantidadAntes = historial.size();

		assertThrows(UnsupportedOperationException.class, () -> historial.clear());
		assertEquals(cantidadAntes, cronista.getHistorial().size());
	}

	@Test
	void sinCombatesJugadosLaTablaYElHistorialEstanVacios() {
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		assertTrue(cronista.getHistorial().isEmpty());
		assertTrue(cronista.tablaDePosiciones().isEmpty());
	}
}
