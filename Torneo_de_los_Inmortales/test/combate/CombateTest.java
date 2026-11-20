package combate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import equipamiento_y_tecnicas.CorteDeLosMilAmaneceres;
import equipamiento_y_tecnicas.Tecnica;
import interesado.Notificador;
import torneo.Pareja;
import torneo.Torneo;
import torneo.Combate;

class CombateTest {
	
	Combatiente c1 = new CultivadorDeLaEspada(1, "Aurelio");
	Combatiente c2 = new HechiceroDelVacio(2, "Aragon");
	Combatiente c3 = new CultivadorDeLaEspada(3, "Rival 1");
	Combatiente c4 = new HechiceroDelVacio(4, "Rival 2");
	Tecnica corte = new CorteDeLosMilAmaneceres();

	Pareja p1 = new Pareja(1, c1, c2);
	Pareja p2 = new Pareja(2, c3, c4);
	Combate comb1 = new Combate(p1, p2, 1, new Notificador());
	

	@Test
	void seEligeElObjetivoConMenorVida() {
		c1.aprenderTecnicas(List.of(corte));
		p1.prepararParaCombate();
		p2.prepararParaCombate();
		c4.recibirDanio(20);//Ahora en la pareja p2, c4 tiene menor vida
		
		assertEquals(comb1.elegirObjetivo(p2), c4);
	}
	
	@Test 
	void EmpateEligeElObjetivoConMenorId() {
		c1.aprenderTecnicas(List.of(corte));
		p1.prepararParaCombate();
		p2.prepararParaCombate();
		
		//En esta prueba c4 y c3, tienen la misma vida
		assertEquals(comb1.elegirObjetivo(p2), c3);
	}
	
	@Test
	void DesempateAlLimiteDeAsaltos() {
		c1.aprenderTecnicas(List.of(corte));
		p1.prepararParaCombate();
		p2.prepararParaCombate();
		c4.recibirDanio(20);//Ahora en la pareja p2, c4 tiene menor vida, por lo que p2 tiene menor vida
		
		
		assertEquals(comb1.ganadorPorVida(), p1);
		
	}
}
