package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import equipamiento_y_tecnicas.Equipamiento;
import equipamiento_y_tecnicas.EspadaCelestial;
import java.util.List;


class equipamientoTest {
	
	Combatiente c1;
	EspadaCelestial e1;
	
	@BeforeEach
	void EsteMetodoSeEjecutaAntesDeCadaTest() {
		 c1 = new CultivadorDeLaEspada(1, "Aurelio");
		 e1 = new EspadaCelestial();
		 c1.equipar(e1);
		 
	}
	
	@Test
	void equipamientoContieneEspadaCelestial() {
	    List<Equipamiento> equipamiento = c1.getEquipamiento();
	    assertEquals(1, equipamiento.size());
	    assertEquals("Espada celestial", equipamiento.get(0).getNombre());
	}
	
	@Test
	void danioAumenta() {
		int esperado = 15; //danio inicial = 10 + 5 (Espada celestial)
		assertEquals(c1.getDanio(), esperado);	
	}
	
	@Test 
	void desequiparRestauraDañoBase() {
		c1.desequipar(e1);
		int danioEsperado = 10;
		assertEquals(10, danioEsperado);
		
	}

}
