package efectos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;

class CuracionTest {

	@Test
	void curaCombatienteConVidaReducida() {
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio"); //la vida inicial es 100 en todos los casos
		Combatiente afectado = new CultivadorDeLaEspada(2, "Kael"); 
		ejecutor.prepararParaCombate();
		afectado.prepararParaCombate();
		afectado.recibirDanio(50);

		Curacion curacion = new Curacion(30);
		curacion.aplicarEfecto(ejecutor, afectado);

		assertEquals(80, afectado.getVidaActual());
		assertEquals(Combatiente.VIDA_INICIAL, ejecutor.getVidaActual());
	}

	@Test
	void curacionNoSuperaLaVidaMaxima() {
		Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");
		combatiente.prepararParaCombate();
		combatiente.recibirDanio(20);

		Curacion curacion = new Curacion(50);
		curacion.aplicarEfecto(combatiente, combatiente);

		assertEquals(combatiente.getVidaMaxima(), combatiente.getVidaActual());
	}

	@Test
	void curacionConVidaCompletaMantieneVidaMaxima() {
		Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");
		combatiente.prepararParaCombate();

		Curacion curacion = new Curacion(25);
		curacion.aplicarEfecto(combatiente, combatiente);

		assertEquals(combatiente.getVidaMaxima(), combatiente.getVidaActual());
	}

	@Test
	void noCuraCombatienteFueraDeCombate() {
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente afectado = new CultivadorDeLaEspada(2, "Kael");
		ejecutor.prepararParaCombate();
		afectado.prepararParaCombate();
		afectado.recibirDanio(Combatiente.VIDA_INICIAL);

		Curacion curacion = new Curacion(30);

		assertThrows(IllegalStateException.class, () -> curacion.aplicarEfecto(ejecutor, afectado));
	}

	@Test
	void noCuraCombatienteQueNoInicioCombate() {
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente afectado = new CultivadorDeLaEspada(2, "Kael");
		ejecutor.prepararParaCombate();

		Curacion curacion = new Curacion(30);

		assertThrows(IllegalStateException.class, () -> curacion.aplicarEfecto(ejecutor, afectado));
	}

	@Test
	void curacionConCantidadNoPositivaLanzaExcepcion() {
		Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");
		combatiente.prepararParaCombate();
		combatiente.recibirDanio(20);

		assertThrows(IllegalArgumentException.class, () -> new Curacion(0).aplicarEfecto(combatiente, combatiente));
		assertThrows(IllegalArgumentException.class, () -> new Curacion(-10).aplicarEfecto(combatiente, combatiente));
	}

}
