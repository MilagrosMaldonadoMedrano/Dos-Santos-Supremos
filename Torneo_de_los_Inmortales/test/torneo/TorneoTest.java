package torneo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import combatiente.MonjeDelCuerpoCelestial;
import excepciones.InscripcionInvalidaException;
import excepciones.TorneoInvalidoException;
import interesado.CronistaDeLaSecta;
import interesado.ResultadoCombate;
import interesado.Posicion;

class TorneoTest {

	Combatiente c1 = new CultivadorDeLaEspada(1, "Aurelio");
	Combatiente c2 = new HechiceroDelVacio(2, "Aragon");
	Combatiente c3 = new CultivadorDeLaEspada(3, "Rival 1");
	Combatiente c4 = new HechiceroDelVacio(4, "Rival 2");
	Combatiente c5 = new CultivadorDeLaEspada(5, "Rival 5");
	Combatiente c6 = new HechiceroDelVacio(6, "Rival 6");
	Combatiente c7 = new MonjeDelCuerpoCelestial(7, "Rival 5");
	Combatiente c8 = new HechiceroDelVacio(8, "Rival 6");
	CronistaDeLaSecta cronista = new CronistaDeLaSecta();
	Torneo t1 = new Torneo();
	Torneo t2 = new Torneo();
	Torneo t3 = new Torneo();
	Torneo t4 = new Torneo();

	Pareja p1 = new Pareja(1, c1, c2);
	Pareja p2 = new Pareja(2, c3, c4);
	Pareja p3 = new Pareja(3, c5, c6);
	Pareja p4 = new Pareja(4, c7, c8);
	
	@Test
	void InscripcionDupplicadaSeRehaza() throws InscripcionInvalidaException {
		t1.inscribir(p1);
		
		assertThrows(InscripcionInvalidaException.class, () -> t1.inscribir(p1));
	}
	
	@Test
	void cantidadParejasInvalidas() throws TorneoInvalidoException, InscripcionInvalidaException {
		t1.inscribir(p1);
		t1.inscribir(p2);
		
		assertThrows(TorneoInvalidoException.class, () -> t1.iniciar());
		
	}
	
	@Test
	void tablaOrdenadaPorPuntosDeVida() {
	    p2.prepararParaCombate();
	    c4.recibirDanio(20); // p2 queda con menos vida

	    ResultadoCombate r = new ResultadoCombate(0, p1, p1, p2);

	    List<Posicion> tablaEsperada = new ArrayList<>();
	    // p1 tiene más vida (200) -> va primero
	    tablaEsperada.add(new Posicion(p1.getId(), r.getIntegrantesA(), r.getRonda(), r.getVidaA()));
	    // p2 tiene menos vida (180) -> va segundo
	    tablaEsperada.add(new Posicion(p2.getId(), r.getIntegrantesB(), r.getRonda(), r.getVidaB()));

	    cronista.resultadoCombate(r);

	    assertEquals(tablaEsperada.toString(), cronista.tablaDePosiciones().toString());
	}
	
	@Test
	void ordenaPorRondaCuandoVidaEsIgual() {
	    p1.prepararParaCombate();
	    p2.prepararParaCombate();
	    p3.prepararParaCombate();

	    ResultadoCombate r1 = new ResultadoCombate(1, p1, p1, p2);
	    ResultadoCombate r2 = new ResultadoCombate(2, p1, p1, p3);
	    ResultadoCombate r3 = new ResultadoCombate(3, p2, p2, p3);

	    List<Posicion> tablaEsperada = new ArrayList<>();
	    tablaEsperada.add(new Posicion(p2.getId(), r3.getIntegrantesA(), r3.getRonda(), r3.getVidaA())); // r3
	    tablaEsperada.add(new Posicion(p3.getId(), r3.getIntegrantesB(), r3.getRonda(), r3.getVidaB())); // r3
	    tablaEsperada.add(new Posicion(p1.getId(), r2.getIntegrantesA(), r2.getRonda(), r2.getVidaA())); // r2

	    cronista.resultadoCombate(r1);
	    cronista.resultadoCombate(r2);
	    cronista.resultadoCombate(r3);

	    assertEquals(tablaEsperada.toString(), cronista.tablaDePosiciones().toString());
	}

	@Test 
	void dosTorneosConMismoArmadoTerminanIgual() throws InscripcionInvalidaException, TorneoInvalidoException {
		
		t1.inscribir(p1);
		t1.inscribir(p2);
		t1.inscribir(p3);
		t1.inscribir(p4);
		
		t2.inscribir(p1);
		t2.inscribir(p2);
		t2.inscribir(p3);
		t2.inscribir(p4);
		
		t3.inscribir(p1);
		t3.inscribir(p2);
		t3.inscribir(p3);
		t3.inscribir(p4);
		
		t4.inscribir(p1);
		t4.inscribir(p2);
		t4.inscribir(p3);
		t4.inscribir(p4);
		
		assertEquals(t1.iniciar(), t2.iniciar());
		assertEquals(t3.iniciar(), t4.iniciar());
	}

}
