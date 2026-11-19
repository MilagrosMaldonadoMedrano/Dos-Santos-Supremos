package equipamiento_y_tecnicas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;

class EquipamientoTest {

	@Test
	void equiparCuartoObjetoSeRechazaSinCambios() {
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

		assertThrows(IllegalStateException.class, () -> combatiente.equipar(cuartoObjeto));
		assertEquals(Combatiente.MAX_EQUIPAMIENTO, combatiente.getEquipamiento().size());
		assertEquals(equipoAntes, combatiente.getEquipamiento());
		assertFalse(combatiente.getEquipamiento().contains(cuartoObjeto));
		assertEquals(vidaMaximaAntes, combatiente.getVidaMaxima());
		assertEquals(energiaMaximaAntes, combatiente.getEnergiaMaxima());
	}

	@Test
	void equiparCategoriaRepetidaSeRechazaSinCambios() {
		Combatiente combatiente = new HechiceroDelVacio(1, "Nébula");
		Equipamiento primerAnillo = new AnilloEspiritual();
		Equipamiento segundoAnillo = new Anillo("Anillo de Jade", 25);

		combatiente.equipar(primerAnillo);

		List<Equipamiento> equipoAntes = List.copyOf(combatiente.getEquipamiento());
		int energiaMaximaAntes = combatiente.getEnergiaMaxima();

		assertThrows(IllegalArgumentException.class, () -> combatiente.equipar(segundoAnillo));
		assertEquals(1, combatiente.getEquipamiento().size());
		assertEquals(equipoAntes, combatiente.getEquipamiento());
		assertFalse(combatiente.getEquipamiento().contains(segundoAnillo));
		assertEquals(energiaMaximaAntes, combatiente.getEnergiaMaxima());
	}
}
