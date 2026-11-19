package main;
import java.util.List;

import combatiente.Combatiente;
import combatiente.CreadorCombatiente;
import equipamiento_y_tecnicas.AnilloEspiritual;
import equipamiento_y_tecnicas.ArmaduraDorada;
import equipamiento_y_tecnicas.CorteDeLosMilAmaneceres;
import equipamiento_y_tecnicas.EspadaCelestial;
import equipamiento_y_tecnicas.SelloDelFenix;
import escuela.Escuela;
import excepciones.EquipamientoInvalidoException;
import excepciones.InscripcionInvalidaException;
import excepciones.TecnicaNoDisponibleException;
import excepciones.TorneoInvalidoException;
import torneo.Pareja;
import torneo.Torneo;


public class App {

	public static void main(String[] args) {

		Escuela espada = new Escuela("Escuela de la Espada Celestial",
				List.of(new CorteDeLosMilAmaneceres()), List.of(new EspadaCelestial()));
		CreadorCombatiente creador = new CreadorCombatiente();
		Torneo torneo = new Torneo();

		try {
			// crear puede lanzar EquipamientoInvalidoException (la escuela equipa)
			Combatiente aurelio = creador.crear(CreadorCombatiente.CULTIVADOR, "Aurelio", espada);
			Combatiente darian = creador.crear(CreadorCombatiente.CULTIVADOR, "Darian", espada);
			Pareja primera = new Pareja(1, aurelio, darian);
			torneo.inscribir(primera);

			// Cada regla en su propio try/catch: se muestra el rechazo y el programa sigue.
			try {
				aurelio.equipar(new EspadaCelestial());          // categoría repetida
			} catch (EquipamientoInvalidoException e) {
				System.out.println("Equipamiento rechazado: " + e.getMessage());
			}

			try {
				aurelio.equipar(new ArmaduraDorada());
				aurelio.equipar(new AnilloEspiritual());
				aurelio.equipar(new SelloDelFenix());            // cuarta pieza
			} catch (EquipamientoInvalidoException e) {
				System.out.println("Equipamiento rechazado: " + e.getMessage());
			}

			try {
				aurelio.usarTecnica(new CorteDeLosMilAmaneceres(), darian);  // fuera de combate
			} catch (TecnicaNoDisponibleException e) {
				System.out.println("Técnica rechazada: " + e.getMessage());
			}

			try {
				torneo.inscribir(primera);                       // pareja repetida
			} catch (InscripcionInvalidaException e) {
				System.out.println("Inscripción rechazada: " + e.getMessage());
			}

			torneo.iniciar();                                    // solo 1 pareja: se rechaza

		} catch (EquipamientoInvalidoException e) {
			System.out.println("Error al preparar un combatiente: " + e.getMessage());
		} catch (InscripcionInvalidaException e) {
			System.out.println("Inscripción rechazada: " + e.getMessage());
		} catch (TorneoInvalidoException e) {
			System.out.println("No se pudo iniciar el torneo: " + e.getMessage());
		} finally {
			System.out.println("Fin de la prueba de excepciones");   // finally siempre se ejecuta
		}
	}
}