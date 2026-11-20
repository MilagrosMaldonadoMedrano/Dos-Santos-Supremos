package torneo;

import java.util.Objects;
import java.util.Optional;

import combatiente.Combatiente;
import equipamiento_y_tecnicas.Tecnica;
import equipamiento_y_tecnicas.TipoTecnica;
import excepciones.TecnicaNoDisponibleException;
import interesado.AccionRealizada;
import interesado.Eliminacion;
import interesado.InicioCombate;
import interesado.Notificador;
import interesado.ResultadoCombate;

// Desarrollo de un cruce entre dos parejas, dividido en asaltos (§6).
public class Combate {
	// Valores definidos por el grupo (van al informe).
	public static final int LIMITE_ASALTOS = 30;
	public static final int ENERGIA_POR_ASALTO = 10;

	private final Pareja primera;   // la de menor id: empieza cada asalto (§6)
	private final Pareja segunda;
	private final Notificador notificador;   // Observer: avisa los acontecimientos
	private final int ronda;
	private int asalto;
	private Pareja ganadora;

	public Combate(Pareja a, Pareja b, int ronda, Notificador notificador) {
		Objects.requireNonNull(a, "La pareja no puede ser null");
		Objects.requireNonNull(b, "La pareja no puede ser null");
		if (a.equals(b)) {
			throw new IllegalArgumentException("Una pareja no puede combatir contra sí misma");
		}
		if (a.getId() < b.getId()) {
			this.primera = a;
			this.segunda = b;
		} else {
			this.primera = b;
			this.segunda = a;
		}
		this.ronda = ronda;
		this.notificador = Objects.requireNonNull(notificador, "El notificador no puede ser null");
		this.asalto = 0;
	}

	public Pareja jugar() {
		if (ganadora != null) {
			throw new IllegalStateException("Este combate ya se jugó");
		}
		primera.prepararParaCombate();
		segunda.prepararParaCombate();
		notificador.publicar(new InicioCombate(ronda, primera, segunda));

		while (!terminado() && asalto < LIMITE_ASALTOS) {
			asalto++;
			jugarAsalto();
		}

		if (terminado()) {
			ganadora = primera.tieneIntegrantesEnPie() ? primera : segunda;
		} else {
			ganadora = ganadorPorVida();   // se llegó al límite de asaltos
		}
		// Se avisa ANTES de finalizar: guarda la vida total de cada pareja en ese momento (§7).
		notificador.publicar(new ResultadoCombate(ronda, ganadora, primera, segunda));

		primera.finalizarCombate();
		segunda.finalizarCombate();
		return ganadora;
	}

	private void jugarAsalto() {
		recuperarEnergia(primera);
		recuperarEnergia(segunda);
		jugarTurno(primera, segunda);
		if (!terminado()) {
			jugarTurno(segunda, primera);
		}
	}

	// El for sobre la pareja recorre solo a los que están en pie (Iterator).
	private void recuperarEnergia(Pareja pareja) {
		for (Combatiente c : pareja) {
			c.recuperarEnergia(ENERGIA_POR_ASALTO);
		}
	}

	private void jugarTurno(Pareja propia, Pareja rival) {
		for (Combatiente c : propia) {
			if (terminado()) {
				return;
			}
			c.procesarEfectosTurno();   // Bonus: veneno o regeneración al comenzar su turno
			if (!c.puedeActuar()) {
				// el veneno lo dejó fuera de combate
				notificador.publicar(new Eliminacion(ronda, asalto, c, propia));
			} else if (!terminado()) {
				actuar(c, propia, rival);
			}
		}
	}

	private void actuar(Combatiente c, Pareja propia, Pareja rival) {
		Combatiente objetivo = elegirObjetivo(rival);
		Optional<Tecnica> elegida = c.elegirTecnica(propia.tecnicasBloqueadasPara(c));
		String accion = "ataque básico";
		Combatiente afectado = objetivo;
		if (elegida.isPresent()) {
			Tecnica tecnica = elegida.get();
			try {
				c.usarTecnica(tecnica, objetivo);
				propia.registrarUso(c, tecnica);
				accion = tecnica.getNombre();
				// las defensivas y de recuperación se aplican al ejecutor (§6)
				if (tecnica.getTipoTecnica() != TipoTecnica.OFENSIVA) {
					afectado = c;
				}
			} catch (TecnicaNoDisponibleException e) {
				c.atacar(objetivo);   // si la técnica no se pudo usar, ataque básico
			}
		} else {
			c.atacar(objetivo);
		}
		notificador.publicar(new AccionRealizada(ronda, asalto, c, accion, afectado));
		if (!objetivo.puedeActuar()) {
			notificador.publicar(new Eliminacion(ronda, asalto, objetivo, rival));
		}
	}

	// §6: el rival en pie con menos vida; en empate, el de menor id.
	public Combatiente elegirObjetivo(Pareja rival) {
		Combatiente elegido = null;
		for (Combatiente c : rival) {
			if (elegido == null || c.getVidaActual() < elegido.getVidaActual()) {
				elegido = c;
			}
		}
		return elegido;
	}

	// §6: al llegar al límite gana la de más vida total; en empate, la de menor id.
	public Pareja ganadorPorVida() {
		if (segunda.vidaTotal() > primera.vidaTotal()) {
			return segunda;
		}
		return primera;
	}

	public boolean terminado() {
		return !primera.tieneIntegrantesEnPie() || !segunda.tieneIntegrantesEnPie();
	}

	public Pareja getGanadora() { return ganadora; }
	public Pareja getPrimera() { return primera; }
	public Pareja getSegunda() { return segunda; }
	public int getRonda() { return ronda; }
	public int getAsaltosJugados() { return asalto; }
}