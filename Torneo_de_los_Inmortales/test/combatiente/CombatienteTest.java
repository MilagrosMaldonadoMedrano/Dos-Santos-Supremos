package combatiente;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import equipamiento_y_tecnicas.CorteDeLosMilAmaneceres;
import equipamiento_y_tecnicas.Tecnica;

class CombatienteTest {

	@Test
	void unCaidoNoPuedeActuar() {
		Combatiente caido = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente rival = new CultivadorDeLaEspada(2, "Rival");
		Tecnica corte = new CorteDeLosMilAmaneceres();

		caido.aprenderTecnicas(List.of(corte));
		caido.prepararParaCombate();
		rival.prepararParaCombate();

		caido.recibirDanio(Combatiente.VIDA_INICIAL);

		assertEquals(0, caido.getVidaActual());
		assertFalse(caido.puedeActuar());
		assertFalse(caido.puedeUsar(corte));
		assertTrue(caido.elegirTecnica(Set.of()).isEmpty());

		assertThrows(IllegalStateException.class, () -> caido.atacar(rival));
		assertThrows(IllegalStateException.class, () -> caido.usarTecnica(corte, rival));
		assertThrows(IllegalStateException.class, () -> caido.curar(20));

		assertEquals(Combatiente.VIDA_INICIAL, rival.getVidaActual());
		assertEquals(Combatiente.ENERGIA_INICIAL, caido.getEnergiaActual());
		assertEquals(Combatiente.USOS_POR_TECNICA, caido.usosRestantes(corte));
	}

	@Test
	void unCaidoNoPuedeSerElegidoComoObjetivo() {
		Combatiente atacante = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente caido = new CultivadorDeLaEspada(2, "Rival");
		Tecnica corte = new CorteDeLosMilAmaneceres();

		atacante.aprenderTecnicas(List.of(corte));
		atacante.prepararParaCombate();
		caido.prepararParaCombate();

		caido.recibirDanio(Combatiente.VIDA_INICIAL);

		assertFalse(caido.puedeSerObjetivo());
		assertThrows(IllegalStateException.class, () -> atacante.atacar(caido));
		assertThrows(IllegalStateException.class, () -> atacante.usarTecnica(corte, caido));
		assertThrows(IllegalStateException.class, () -> caido.recibirDanio(10));

		assertEquals(Combatiente.ENERGIA_INICIAL, atacante.getEnergiaActual());
		assertEquals(Combatiente.USOS_POR_TECNICA, atacante.usosRestantes(corte));
	}

}
