package main;

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
import equipamiento_y_tecnicas.VenenoDeLaSerpiente;
import escuela.Escuela;
import excepciones.EquipamientoInvalidoException;
import excepciones.InscripcionInvalidaException;
import excepciones.TorneoInvalidoException;
import interesado.CronistaDeLaSecta;
import interesado.HeraldoImperial;
import java.util.List;
import torneo.Pareja;
import torneo.Torneo;

/*
*SE VA A PROBAR EQUIPOS CON LOS MISMO EQUIPAMIENTOS Y TECNICAS, PERO CON DIFERENTES COMBATIENTES, PARA VER 
*SI EL POLIMORFISMO FUNCIONA CORRECTAMENTE Y LA REGLA DEL COMPAÑERO.
*/

public class App3 {
    public static void main(String[] args) {
		HeraldoImperial heraldo = new HeraldoImperial(System.out);
		CronistaDeLaSecta cronista = new CronistaDeLaSecta();

		// ESCUELAS
		Escuela escuelaDeLaEspada = new Escuela("Escuela de la Espada Celestial",
				List.of(new CorteDeLosMilAmaneceres(), new RugidoDeLaBestiaPrimordial()),
				List.of(new EspadaCelestial()));
		Escuela palacioDelVacio = new Escuela("Palacio del Vacío",
				List.of(new DominioDeLaNada(), new MeditacionDelInmortal(), new VenenoDeLaSerpiente()),
				List.of(new AnilloEspiritual()));
		Escuela monasterio = new Escuela("Monasterio del Cuerpo Dorado",
				List.of(new MuroDelCuerpoDorado(), new MeditacionDelInmortal()),
				List.of(new ArmaduraDorada(), new SelloDelFenix()));

		try {
			// CREACIÓN DE COMBATIENTES
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
			// Observer: el Heraldo y el Cronista se suscriben como interesados.
			torneo.suscribir(heraldo);
			torneo.suscribir(cronista);
             // PRIMEROS 3 EQUIPOS CON LOS MISMOS EQUIPAMIENTOS Y TECNICAS, PERO DIFERENTES COMBATIENTES
			torneo.inscribir(new Pareja(1, aurelio, darian)); 
			torneo.inscribir(new Pareja(2, nebula, lyra));
			torneo.inscribir(new Pareja(3, kael, selene));
			torneo.inscribir(new Pareja(4, tharos, orin));

			// Un combatiente no puede inscribirse dos veces: se muestra el rechazo y se sigue.
			try {
				torneo.inscribir(new Pareja(5, aurelio, orin));
			} catch (InscripcionInvalidaException e) {
				heraldo.anunciar("Inscripción rechazada: " + e.getMessage());
			}

			// Se juega el torneo; el Heraldo va anunciando todo, incluida la proclamación.
			torneo.iniciar();

			// Al terminar, el main le pide la tabla al Cronista y el Heraldo la presenta.
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
