package equipamiento_y_tecnicas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import combatiente.MonjeDelCuerpoCelestial;

class CorteDeLosMilAmaneceresTest {

	@Test
	void unaMismaTecnicaDaniaDiferenteSegunElTipoDelEjecutor() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();

		Combatiente cultivador = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente hechicero = new HechiceroDelVacio(2, "Nébula");
		Combatiente objetivoParaCultivador = new HechiceroDelVacio(3, "Rival 1");
		Combatiente objetivoParaHechicero = new HechiceroDelVacio(4, "Rival 2");

		cultivador.aprenderTecnicas(List.of(corte));
		hechicero.aprenderTecnicas(List.of(corte));

		cultivador.prepararParaCombate();
		hechicero.prepararParaCombate();
		objetivoParaCultivador.prepararParaCombate();
		objetivoParaHechicero.prepararParaCombate();

		cultivador.usarTecnica(corte, objetivoParaCultivador);
		hechicero.usarTecnica(corte, objetivoParaHechicero);

		int danioCultivador = Combatiente.VIDA_INICIAL - objetivoParaCultivador.getVidaActual();
		int danioHechicero = Combatiente.VIDA_INICIAL - objetivoParaHechicero.getVidaActual();

		assertEquals(25, danioCultivador);
		assertEquals(20, danioHechicero);
		assertNotEquals(danioCultivador, danioHechicero);
	}

	@Test
	void noSeEjecutaSinEnergiaSuficiente() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new MonjeDelCuerpoCelestial(2, "Kael");

		ejecutor.aprenderTecnicas(List.of(corte));
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		// Energia inicial: 40, costo: 15 -> 2 usos dejan 10 de energia
		ejecutor.usarTecnica(corte, objetivo);
		ejecutor.usarTecnica(corte, objetivo);

		int vidaAntes = objetivo.getVidaActual();
		int energiaAntes = ejecutor.getEnergiaActual();
		int usosAntes = ejecutor.usosRestantes(corte);

		assertEquals(10, energiaAntes);
		assertThrows(IllegalStateException.class, () -> ejecutor.usarTecnica(corte, objetivo));
		assertEquals(vidaAntes, objetivo.getVidaActual());
		assertEquals(energiaAntes, ejecutor.getEnergiaActual());
		assertEquals(usosAntes, ejecutor.usosRestantes(corte));
	}

	@Test
	void noSeEjecutaSinUsosRestantes() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente objetivo = new MonjeDelCuerpoCelestial(2, "Kael");

		ejecutor.aprenderTecnicas(List.of(corte));
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		for (int i = 0; i < Combatiente.USOS_POR_TECNICA; i++) {
			ejecutor.usarTecnica(corte, objetivo);
			ejecutor.recuperarEnergia(15);
		}

		int vidaAntes = objetivo.getVidaActual();
		int energiaAntes = ejecutor.getEnergiaActual();

		assertEquals(0, ejecutor.usosRestantes(corte));
		assertTrue(objetivo.puedeSerObjetivo());
		assertThrows(IllegalStateException.class, () -> ejecutor.usarTecnica(corte, objetivo));
		assertEquals(vidaAntes, objetivo.getVidaActual());
		assertEquals(energiaAntes, ejecutor.getEnergiaActual());
	}

	@Test
	void descuentaEnergiaYUsosAlEjecutarse() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new CultivadorDeLaEspada(2, "Rival");

		ejecutor.aprenderTecnicas(List.of(corte));
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		ejecutor.usarTecnica(corte, objetivo);

		assertEquals(Combatiente.ENERGIA_INICIAL - 15, ejecutor.getEnergiaActual());
		assertEquals(Combatiente.USOS_POR_TECNICA - 1, ejecutor.usosRestantes(corte));
	}

	@Test
	void noSeEjecutaSiElEjecutorNoConoceLaTecnica() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new CultivadorDeLaEspada(2, "Rival");

		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		assertThrows(IllegalStateException.class, () -> ejecutor.usarTecnica(corte, objetivo));
		assertEquals(Combatiente.VIDA_INICIAL, objetivo.getVidaActual());
		assertEquals(Combatiente.ENERGIA_INICIAL, ejecutor.getEnergiaActual());
	}

	@Test
	void noSeEjecutaSiElObjetivoEstaFueraDeCombateYNoConsumeRecursos() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new CultivadorDeLaEspada(2, "Rival");

		ejecutor.aprenderTecnicas(List.of(corte));
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();
		objetivo.recibirDanio(Combatiente.VIDA_INICIAL);

		assertThrows(IllegalStateException.class, () -> ejecutor.usarTecnica(corte, objetivo));
		assertEquals(Combatiente.ENERGIA_INICIAL, ejecutor.getEnergiaActual());
		assertEquals(Combatiente.USOS_POR_TECNICA, ejecutor.usosRestantes(corte));
	}

	@Test
	void noSeEjecutaSiElEjecutorEstaFueraDeCombate() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new CultivadorDeLaEspada(2, "Rival");

		ejecutor.aprenderTecnicas(List.of(corte));
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();
		ejecutor.recibirDanio(Combatiente.VIDA_INICIAL);

		assertThrows(IllegalStateException.class, () -> ejecutor.usarTecnica(corte, objetivo));
		assertEquals(Combatiente.VIDA_INICIAL, objetivo.getVidaActual());
		assertEquals(Combatiente.USOS_POR_TECNICA, ejecutor.usosRestantes(corte));
	}

	@Test
	void losUsosSeConservanEntreCombatesMientrasVidaYEnergiaSeRecuperan() {
		CorteDeLosMilAmaneceres corte = new CorteDeLosMilAmaneceres();
		Combatiente ejecutor = new HechiceroDelVacio(1, "Nébula");
		Combatiente objetivo = new MonjeDelCuerpoCelestial(2, "Kael");

		ejecutor.aprenderTecnicas(List.of(corte));
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		// Primer combate: gasta 2 usos, energia y recibe danio
		ejecutor.usarTecnica(corte, objetivo);
		ejecutor.usarTecnica(corte, objetivo);
		ejecutor.recibirDanio(40);

		assertEquals(60, ejecutor.getVidaActual());
		assertTrue(ejecutor.getEnergiaActual() < ejecutor.getEnergiaMaxima());
		assertEquals(Combatiente.USOS_POR_TECNICA - 2, ejecutor.usosRestantes(corte));

		// Fin del primer combate e inicio del segundo
		ejecutor.finalizarCombate();
		objetivo.finalizarCombate();
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		assertEquals(ejecutor.getVidaMaxima(), ejecutor.getVidaActual());
		assertEquals(ejecutor.getEnergiaMaxima(), ejecutor.getEnergiaActual());
		assertEquals(objetivo.getVidaMaxima(), objetivo.getVidaActual());
		assertEquals(Combatiente.USOS_POR_TECNICA - 2, ejecutor.usosRestantes(corte));

		// Segundo combate: solo quedan los 3 usos restantes del combate anterior
		ejecutor.usarTecnica(corte, objetivo);
		ejecutor.usarTecnica(corte, objetivo);
		ejecutor.usarTecnica(corte, objetivo);
		ejecutor.recuperarEnergia(15);

		assertEquals(0, ejecutor.usosRestantes(corte));
		assertThrows(IllegalStateException.class, () -> ejecutor.usarTecnica(corte, objetivo));
	}
}
