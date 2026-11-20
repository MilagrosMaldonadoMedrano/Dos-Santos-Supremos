package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import efectos.Efecto;
import equipamiento_y_tecnicas.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;

class efectoTest {
	
	Combatiente c1;
    Tecnica tecnica;

    @BeforeEach
    void EsteMetodoSeEjecutaAntesDeCadaTest() {
        c1 = new CultivadorDeLaEspada(1, "Aurelio");
        tecnica = new DominioDeLaNada();
        c1.aprenderTecnicas(List.of(tecnica));
    }

    @Test
    void tecnicaAprendida() {
        List<Tecnica> tecnicas = c1.getTecnicas();
        assertEquals(1, tecnicas.size());
        assertEquals("Dominio de la Nada", tecnicas.get(0).getNombre());
    }
    
    @Test
    void usarGastaEnergia() {
        CultivadorDeLaEspada rival = new CultivadorDeLaEspada(2, "Rival");
        c1.usarTecnica(tecnica, rival);
        assertEquals(6, c1.getEnergiaActual()); // 40 - 34
    }

}
