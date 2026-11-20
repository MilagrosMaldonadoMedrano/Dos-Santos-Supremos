package equipamiento_y_tecnicas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import excepciones.EquipamientoInvalidoException;

class EquipamientoTest {

	@Test
	void equiparCuartoObjetoSeRechazaSinCambios() throws EquipamientoInvalidoException {
		Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");
		Equipamiento arma = new EspadaCelestial();
		Equipamiento armadura = new ArmaduraDorada();
		Equipamiento anillo = new AnilloEspiritual();
		Equipamiento cuartoObjeto = new SelloDelFenix();

		combatiente.equipar(arma);
		combatiente.equipar(armadura);
		combatiente.equipar(anillo);

		List<Equipamiento> equipoAntes = List.copyOf(combatiente.getEquipamiento());
		int vidaMaximaAntes = combatiente.getVidaMaxima();			//el sello del fenix aumenta la vida maxima
		int energiaMaximaAntes = combatiente.getEnergiaMaxima(); 	//el anillo espiritual aumenta la energia maxima

		assertThrows(EquipamientoInvalidoException.class, () -> combatiente.equipar(cuartoObjeto));
		assertEquals(Combatiente.MAX_EQUIPAMIENTO, combatiente.getEquipamiento().size());
		assertEquals(equipoAntes, combatiente.getEquipamiento());
		assertFalse(combatiente.getEquipamiento().contains(cuartoObjeto));
		assertEquals(vidaMaximaAntes, combatiente.getVidaMaxima());
		assertEquals(energiaMaximaAntes, combatiente.getEnergiaMaxima());
	}

	@Test
	void equiparCategoriaRepetidaSeRechazaSinCambios() throws EquipamientoInvalidoException {
		Combatiente combatiente = new HechiceroDelVacio(1, "Nébula");
		Equipamiento primerAnillo = new AnilloEspiritual();
		Equipamiento segundoAnillo = new Anillo("Anillo de Jade", 25);

		combatiente.equipar(primerAnillo);

		List<Equipamiento> equipoAntes = List.copyOf(combatiente.getEquipamiento());
		int energiaMaximaAntes = combatiente.getEnergiaMaxima();

		assertThrows(EquipamientoInvalidoException.class, () -> combatiente.equipar(segundoAnillo));
		assertEquals(1, combatiente.getEquipamiento().size());
		assertEquals(equipoAntes, combatiente.getEquipamiento());
		assertFalse(combatiente.getEquipamiento().contains(segundoAnillo));
		assertEquals(energiaMaximaAntes, combatiente.getEnergiaMaxima());
	}
	
	@Test
	void sePuedeEquiparYDesequiparEntreCombates() throws EquipamientoInvalidoException {
	    Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");

	    Equipamiento espada = new EspadaCelestial();
	    Equipamiento armadura = new ArmaduraDorada();

	    // Antes del combate: equipar
	    combatiente.equipar(espada);
	    assertEquals(1, combatiente.getEquipamiento().size());

	    // Durante el combate: ni equipar ni desequipar
	    combatiente.prepararParaCombate();
	    assertThrows(EquipamientoInvalidoException.class, () -> combatiente.equipar(armadura));
	    assertThrows(EquipamientoInvalidoException.class, () -> combatiente.desequipar(espada));

	    // Entre combates: desequipar y volver a equipar
	    combatiente.finalizarCombate();

	    combatiente.desequipar(espada);
	    assertTrue(combatiente.getEquipamiento().isEmpty());

	    combatiente.equipar(armadura);
	    assertEquals(1, combatiente.getEquipamiento().size());
	    assertTrue(combatiente.getEquipamiento().contains(armadura));
	}

	@Test
	void equiparObjetoQueAumentaElMaximoNoAumentaActual() throws EquipamientoInvalidoException {
	    Combatiente combatiente = new CultivadorDeLaEspada(1, "Aurelio");

	    // Estado inicial: vidaActual == vidaMaxima == 100
	    int vidaMaximaAntes = combatiente.getVidaMaxima();
	    int vidaActualAntes = combatiente.getVidaActual();
	    assertEquals(vidaMaximaAntes, vidaActualAntes);

	    Equipamiento sello = new SelloDelFenix();
	    combatiente.equipar(sello);

	    // El máximo sube
	    assertTrue(combatiente.getVidaMaxima() > vidaMaximaAntes,
	        "El SelloDelFenix debe aumentar la vida máxima");
	    // El actual NO cambia
	    assertEquals(vidaActualAntes, combatiente.getVidaActual(),
	        "Equipar no debe curar: la vida actual no cambia");
	}
	
	@Test
	void desequiparAnilloRecortaEnergiaActualAlNuevoMaximo() throws EquipamientoInvalidoException {
	    Combatiente combatiente = new HechiceroDelVacio(1, "Nébula");

	    int energiaMaximaBase = combatiente.getEnergiaMaxima();

	    Equipamiento anillo = new AnilloEspiritual();
	    combatiente.equipar(anillo);
	    int energiaMaximaConAnillo = combatiente.getEnergiaMaxima();
	    assertTrue(energiaMaximaConAnillo > energiaMaximaBase,
	        "El AnilloEspiritual debe aumentar la energía máxima");

	    // Entrar al combate para llenar la energía actual al máximo (con anillo)
	    combatiente.prepararParaCombate();
	    assertEquals(energiaMaximaConAnillo, combatiente.getEnergiaActual());

	    // Salir y desequipar: el máximo vuelve a la base, el actual se recorta
	    combatiente.finalizarCombate();
	    combatiente.desequipar(anillo);

	    assertEquals(energiaMaximaBase, combatiente.getEnergiaMaxima());
	    assertEquals(energiaMaximaBase, combatiente.getEnergiaActual(),
	        "Al bajar el máximo, la energía actual se recorta al nuevo máximo");
	}
}

