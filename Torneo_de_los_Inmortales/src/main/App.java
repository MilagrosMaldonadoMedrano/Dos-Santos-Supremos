package main;

import combatiente.Combatiente;
import combatiente.CultivadorDeLaEspada;
import combatiente.HechiceroDelVacio;
import combatiente.MonjeDelCuerpoCelestial;

public class App {

	public static void main(String[] args) {

		// crear un combatiente de cada tipo
		Combatiente aurelio = new CultivadorDeLaEspada(1, "Aurelio");
		Combatiente kael = new MonjeDelCuerpoCelestial(2, "Kael");
		Combatiente nebula = new HechiceroDelVacio(3, "Nébula");

		// Comienza el combate: pasan de Preparado a EnPie
		aurelio.prepararParaCombate();
		kael.prepararParaCombate();
		nebula.prepararParaCombate();

		// Aurelio (Cultivador) pega 25% más
		aurelio.atacar(nebula);
		System.out.println("Nébula queda con " + nebula.getVidaActual() + " de vida");

		// Kael (Monje) recibe 25% menos
		aurelio.atacar(kael);
		System.out.println("Kael queda con " + kael.getVidaActual() + " de vida");

		// Nébula (Hechicero) paga menos energía
		nebula.gastarEnergia(20);
		System.out.println("Nébula gastó una técnica de 20 y le queda " + nebula.getEnergiaActual() + " de energía");

		// Kael activa una defensa y recibe menos daño
		kael.activarDefensa(5);
		aurelio.atacar(kael);
		System.out.println("Kael con defensa queda con " + kael.getVidaActual() + " de vida");

		// Nébula cae: pasa a FueraDeCombate
		while (nebula.puedeSerObjetivo()) {
			aurelio.atacar(nebula);
		}
		System.out.println("Nébula puede actuar: " + nebula.puedeActuar());

		// Termina el combate y empieza otro: todos vuelven al máximo
		aurelio.finalizarCombate();
		kael.finalizarCombate();
		nebula.finalizarCombate();
		nebula.prepararParaCombate();
		System.out.println("Nuevo combate: Nébula vuelve con " + nebula.getVidaActual() + " de vida");
	}
}