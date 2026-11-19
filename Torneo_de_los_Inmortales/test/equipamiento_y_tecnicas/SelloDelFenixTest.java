package equipamiento_y_tecnicas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;

class SelloDelFenixTest {

	@Test
	void alQuitarEquipamientoQueSubeVidaMaximaLaVidaActualSeRecorta() {
		Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");
		SelloDelFenix sello = new SelloDelFenix();

		combatiente.equipar(sello);
		assertEquals(Combatiente.VIDA_INICIAL + sello.getVidaExtra(), combatiente.getVidaMaxima());
		assertEquals(Combatiente.VIDA_INICIAL, combatiente.getVidaActual());

		combatiente.prepararParaCombate(); //al preparlo para el combate se le regenera la vida hasta la vida maxima. Que seria 120
		assertEquals(Combatiente.VIDA_INICIAL + sello.getVidaExtra(), combatiente.getVidaActual());

		combatiente.finalizarCombate();
		combatiente.desequipar(sello);

		assertEquals(Combatiente.VIDA_INICIAL, combatiente.getVidaMaxima());
		assertEquals(Combatiente.VIDA_INICIAL, combatiente.getVidaActual());
	}

	@Test
	void alQuitarEquipamientoConVidaActualMenorAlNuevoMaximoNoSeModificaLaVidaActual() {
		Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");
		SelloDelFenix sello = new SelloDelFenix();

		combatiente.equipar(sello); // tiene 100 + 20 de vida maxima
		combatiente.prepararParaCombate(); // lo cura hasta 120
		combatiente.recibirDanio(30); // queda con 120-30 = 90 de vida actual
		combatiente.finalizarCombate();

		combatiente.desequipar(sello);

		assertEquals(Combatiente.VIDA_INICIAL, combatiente.getVidaMaxima());
		assertEquals(90, combatiente.getVidaActual());
	}

}
