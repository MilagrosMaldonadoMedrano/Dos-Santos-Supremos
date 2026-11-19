package main;

import java.util.List;

import combatiente.Combatiente;
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
import excepciones.EquipamientoInvalidoException;
import excepciones.InscripcionInvalidaException;
import excepciones.TorneoInvalidoException;
import interesado.CronistaDeLaSecta;
import interesado.HeraldoImperial;
import torneo.Pareja;
import torneo.Torneo;


public class App {

	public static void main(String[] args) {
		HeraldoImperial heraldo = new HeraldoImperial(System.out);
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		// Escuela = configuración: técnicas y equipamiento iniciales.
		Escuela escuelaDeLaEspada = new Escuela("Escuela de la Espada Celestial",
				List.of(new CorteDeLosMilAmaneceres(), new RugidoDeLaBestiaPrimordial()),
				List.of(new EspadaCelestial()));
		Escuela palacioDelVacio = new Escuela("Palacio del Vacío",
				List.of(new DominioDeLaNada(), new MeditacionDelInmortal()),
				List.of(new AnilloEspiritual()));
		Escuela monasterio = new Escuela("Monasterio del Cuerpo Dorado",
				List.of(new MuroDelCuerpoDorado(), new MeditacionDelInmortal()),
				List.of(new ArmaduraDorada(), new SelloDelFenix()));

		try {
			// La creación de combatientes está centralizada (Factory).
			CreadorCombatiente creador = new CreadorCombatiente();
			Combatiente aurelio = creador.crear(CreadorCombatiente.CULTIVADOR, "Aurelio", escuelaDeLaEspada);
			Combatiente nebula = creador.crear(CreadorCombatiente.HECHICERO, "Nébula", palacioDelVacio);
			Combatiente kael = creador.crear(CreadorCombatiente.MONJE, "Kael", monasterio);
			Combatiente darian = creador.crear(CreadorCombatiente.CULTIVADOR, "Darian", escuelaDeLaEspada);
			Combatiente lyra = creador.crear(CreadorCombatiente.HECHICERO, "Lyra", palacioDelVacio);
			Combatiente tharos = creador.crear(CreadorCombatiente.MONJE, "Tharos", escuelaDeLaEspada);
			Combatiente selene = creador.crear(CreadorCombatiente.CULTIVADOR, "Selene", monasterio);
			Combatiente orin = creador.crear(CreadorCombatiente.HECHICERO, "Orin", palacioDelVacio);

			Torneo torneo = new Torneo();
			// Registrar al Heraldo y al Cronista como interesados (Observer).
			torneo.suscribir(heraldo);
			torneo.suscribir(cronista);

			torneo.inscribir(new Pareja(1, aurelio, nebula));
			torneo.inscribir(new Pareja(2, kael, darian));
			torneo.inscribir(new Pareja(3, lyra, tharos));
			torneo.inscribir(new Pareja(4, selene, orin));

			// Regla 5: un combatiente no puede inscribirse dos veces. Se muestra y se sigue.
			try {
				torneo.inscribir(new Pareja(5, aurelio, orin));
			} catch (InscripcionInvalidaException e) {
				heraldo.anunciar("Inscripción rechazada: " + e.getMessage());
			}

			torneo.iniciar();   // el Heraldo va anunciando todo, incluida la proclamación

			// Al terminar, el main le pide la tabla al Cronista y el Heraldo la presenta (7).
			heraldo.presentarTabla(cronista.tablaDePosiciones());

		} catch (EquipamientoInvalidoException e) {
			heraldo.anunciar("Error al preparar un combatiente: " + e.getMessage());
		} catch (InscripcionInvalidaException e) {
			heraldo.anunciar("Inscripción rechazada: " + e.getMessage());
		} catch (TorneoInvalidoException e) {
			heraldo.anunciar("No se pudo iniciar el torneo: " + e.getMessage());
		}
	}
}