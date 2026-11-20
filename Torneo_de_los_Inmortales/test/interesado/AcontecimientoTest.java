package interesado;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import combatiente.MonjeDelCuerpoCelestial;
import torneo.Pareja;

class AcontecimientoTest {

	static Pareja parejaDePrueba() {
		return new Pareja(1, new CultivadorDeLaEspada(1, "Aurelio"), new HechiceroDelVacio(2, "Nébula"));
	}

	@Test
	void laAccionRealizadaGuardaLaVidaDelObjetivoEnEseMomento() {
		Combatiente ejecutor = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente objetivo = new MonjeDelCuerpoCelestial(2, "Kael");
		ejecutor.prepararParaCombate();
		objetivo.prepararParaCombate();

		ejecutor.atacar(objetivo);
		int vidaDespuesDelAtaque = objetivo.getVidaActual();
		AccionRealizada accion = new AccionRealizada(1, 1, ejecutor, "ataque básico", objetivo);

		// el combate sigue y el objetivo vuelve a recibir daño
		objetivo.recibirDanio(30);

		assertEquals(vidaDespuesDelAtaque, accion.getVidaObjetivo());
		assertNotEquals(objetivo.getVidaActual(), accion.getVidaObjetivo());
		assertEquals("Aurelio", accion.getEjecutor());
		assertEquals("Kael", accion.getObjetivo());
		assertEquals("ataque básico", accion.getAccion());
	}

	@Test
	void elResultadoGuardaLaVidaTotalDeCadaParejaAlTerminarElCombate() {
		Combatiente aurelio = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente nebula = new HechiceroDelVacio(2, "Nébula");
		Combatiente kael = new MonjeDelCuerpoCelestial(3, "Kael");
		Combatiente darian = new CultivadorDeLaEspada(4, "Darian");
		Pareja primera = new Pareja(1, aurelio, nebula);
		Pareja segunda = new Pareja(2, kael, darian);
		primera.prepararParaCombate();
		segunda.prepararParaCombate();

		aurelio.recibirDanio(40);
		darian.recibirDanio(Combatiente.VIDA_INICIAL); // un caído suma cero
		ResultadoCombate resultado = new ResultadoCombate(1, primera, primera, segunda);

		// al empezar el siguiente combate todos recuperan la vida
		primera.finalizarCombate();
		segunda.finalizarCombate();
		primera.prepararParaCombate();
		segunda.prepararParaCombate();

		assertEquals(160, resultado.getVidaA());
		assertEquals(100, resultado.getVidaB());
		assertEquals(1, resultado.getIdGanadora());
		assertEquals(1, resultado.getRonda());
		assertEquals(200, primera.vidaTotal());
	}

	@Test
	void laEliminacionGuardaQuienCayoYDeQueParejaEra() {
		Combatiente kael = new MonjeDelCuerpoCelestial(3, "Kael");
		Combatiente darian = new CultivadorDeLaEspada(4, "Darian");
		Pareja pareja = new Pareja(2, kael, darian);
		pareja.prepararParaCombate();

		darian.recibirDanio(Combatiente.VIDA_INICIAL);
		Eliminacion eliminacion = new Eliminacion(1, 5, darian, pareja);

		assertEquals("Darian", eliminacion.getCaido());
		assertEquals(2, eliminacion.getIdPareja());
		assertEquals(5, eliminacion.getAsalto());
	}

	@Test
	void elInicioYLaProclamacionGuardanLasParejasDelMomento() {
		Pareja primera = parejaDePrueba();
		Pareja segunda = new Pareja(2, new MonjeDelCuerpoCelestial(3, "Kael"), new CultivadorDeLaEspada(4, "Darian"));

		InicioCombate inicio = new InicioCombate(1, primera, segunda);
		Proclamacion proclamacion = new Proclamacion(2, primera);

		assertEquals(1, inicio.getIdParejaA());
		assertEquals(2, inicio.getIdParejaB());
		assertEquals("Aurelio y Nébula", inicio.getIntegrantesA());
		assertEquals("Kael y Darian", inicio.getIntegrantesB());
		assertEquals(1, proclamacion.getIdPareja());
		assertEquals("Aurelio y Nébula", proclamacion.getIntegrantes());
		assertEquals(2, proclamacion.getRonda());
	}
}
