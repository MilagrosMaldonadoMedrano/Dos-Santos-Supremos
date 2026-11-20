package torneo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import combatiente.MonjeDelCuerpoCelestial;
import equipamiento_y_tecnicas.CorteDeLosMilAmaneceres;
import equipamiento_y_tecnicas.Tecnica;
import excepciones.InscripcionInvalidaException;
import excepciones.TecnicaNoDisponibleException;

class ParejaTest {

	Tecnica corte = new CorteDeLosMilAmaneceres();

	Combatiente c1 = new CultivadorDeLaEspada(1, "Aurelio");
	Combatiente c2 = new HechiceroDelVacio(2, "Nébula");
	Combatiente rival1 = new MonjeDelCuerpoCelestial(3, "Kael");
	Combatiente rival2 = new MonjeDelCuerpoCelestial(4, "Darian");

	Pareja pareja = new Pareja(1, c1, c2);
	Pareja rivales = new Pareja(2, rival1, rival2);

	// Los dos integrantes conocen la misma técnica y arrancan el combate
	private void prepararCombate() {
		c1.aprenderTecnicas(List.of(corte));
		c2.aprenderTecnicas(List.of(corte));
		pareja.prepararParaCombate();
		rivales.prepararParaCombate();
	}

	// Ejecuta la técnica y la registra en la pareja, igual que hace Combate.actuar
	private void usarCorte(Combatiente quien) throws TecnicaNoDisponibleException {
		quien.recuperarEnergia(Combatiente.ENERGIA_INICIAL);
		quien.usarTecnica(corte, rival1);
		pareja.registrarUso(quien, corte);
	}

	@Test
	void elCompaneroNoPuedeUsarUnaTecnicaMientrasElPrimeroConserveUsos() throws TecnicaNoDisponibleException {
		prepararCombate();

		usarCorte(c1);

		assertTrue(c1.usosRestantes(corte) > 0);
		assertFalse(pareja.puedeUsar(c2, corte));
		assertTrue(pareja.tecnicasBloqueadasPara(c2).contains(corte));
		// con el criterio por defecto, el compañero ya no la elige
		assertTrue(c2.elegirTecnica(pareja.tecnicasBloqueadasPara(c2)).isEmpty());
	}

	@Test
	void elQueUsoLaTecnicaPrimeroPuedeSeguirUsandola() throws TecnicaNoDisponibleException {
		prepararCombate();

		usarCorte(c1);

		assertTrue(pareja.puedeUsar(c1, corte));
		assertTrue(pareja.tecnicasBloqueadasPara(c1).isEmpty());
	}

	@Test
	void siElPrimeroQuedaFueraDeCombateElCompaneroPuedeUsarla() throws TecnicaNoDisponibleException {
		prepararCombate();
		usarCorte(c1);

		c1.recibirDanio(Combatiente.VIDA_INICIAL);

		assertFalse(c1.puedeActuar());
		assertTrue(pareja.puedeUsar(c2, corte));
	}

	@Test
	void siElPrimeroAgotaSusUsosElCompaneroPuedeUsarla() throws TecnicaNoDisponibleException {
		prepararCombate();

		for (int i = 0; i < Combatiente.USOS_POR_TECNICA; i++) {
			usarCorte(c1);
		}

		assertEquals(0, c1.usosRestantes(corte));
		assertTrue(pareja.puedeUsar(c2, corte));

		// el compañero la usa y el registro pasa a él: puede seguir usándola
		usarCorte(c2);
		assertTrue(pareja.puedeUsar(c2, corte));
	}

	@Test
	void laRestriccionTerminaConElCombate() throws TecnicaNoDisponibleException {
		prepararCombate();
		usarCorte(c1);
		assertFalse(pareja.puedeUsar(c2, corte));

		pareja.finalizarCombate();
		pareja.prepararParaCombate();

		assertTrue(pareja.puedeUsar(c2, corte));
		assertTrue(pareja.puedeUsar(c1, corte));
	}

	@Test
	void elAtaqueBasicoNoCuentaParaLaReglaDelCompanero() {
		prepararCombate();

		c1.atacar(rival1);

		assertTrue(pareja.tecnicasBloqueadasPara(c2).isEmpty());
		assertTrue(pareja.puedeUsar(c2, corte));
	}

	@Test
	void unCombatienteNoPuedeInscribirseEnDosParejasDistintas() throws InscripcionInvalidaException {
		Torneo torneo = new Torneo();
		torneo.inscribir(pareja);

		Pareja otraConAurelio = new Pareja(3, c1, rival1);

		assertThrows(InscripcionInvalidaException.class, () -> torneo.inscribir(otraConAurelio));
		assertEquals(1, torneo.getParejas().size());
	}
}
