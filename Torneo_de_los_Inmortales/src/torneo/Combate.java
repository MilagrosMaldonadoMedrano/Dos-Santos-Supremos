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

/*
 * Desarrollo de un cruce entre dos parejas, dividido en asaltos. En cada asalto los que
 * están en pie recuperan energía y cada pareja juega su turno, empezando la de menor id.
 * Termina cuando una pareja se queda sin integrantes en pie o al llegar al límite de
 * asaltos. OBSERVER: avisa cada acontecimiento al Notificador.
 * Lo crea: Cruce.resolver (y las pruebas de combate).
 */
public class Combate {
	public static final int LIMITE_ASALTOS = 30;      // valor elegido por el grupo
	public static final int ENERGIA_POR_ASALTO = 10;  // valor elegido por el grupo

	private final Pareja primera;   // la de menor id: empieza cada asalto
	private final Pareja segunda;
	private final Notificador notificador;   // Observer: avisa los acontecimientos
	private final int ronda;
	private int asalto;
	private Pareja ganadora;

	/*
	 * Crea el combate entre dos parejas distintas y ordena cuál empieza (la de menor id).
	 * Si alguna pareja o el notificador son null, o es la misma pareja, lanza una excepción.
	 * Lo usan: Cruce.resolver y CombateTest.
	 */
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

	/*
	 * Juega el combate completo y devuelve la pareja ganadora: prepara a las parejas,
	 * avisa el inicio, juega asaltos hasta que termina o llega al límite, decide la
	 * ganadora (por eliminación o por vida total), avisa el resultado antes de finalizar
	 * (para guardar la vida de ese momento) y vuelve a las parejas a Preparado.
	 * Si ya se jugó, lanza IllegalStateException.
	 * Lo usa: Cruce.resolver.
	 */
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
			ganadora = ganadorPorVida();
		}
		notificador.publicar(new ResultadoCombate(ronda, ganadora, primera, segunda));

		primera.finalizarCombate();
		segunda.finalizarCombate();
		return ganadora;
	}

	/*
	 * Un asalto: todos los que están en pie recuperan energía y cada pareja juega su
	 * turno; la segunda solo si el combate no terminó con el turno de la primera.
	 * Lo usa: jugar.
	 */
	private void jugarAsalto() {
		recuperarEnergia(primera);
		recuperarEnergia(segunda);
		jugarTurno(primera, segunda);
		if (!terminado()) {
			jugarTurno(segunda, primera);
		}
	}

	/*
	 * Cada integrante en pie recupera ENERGIA_POR_ASALTO. El for sobre la pareja
	 * recorre solo a los que están en pie (Iterator).
	 * Lo usa: jugarAsalto.
	 */
	private void recuperarEnergia(Pareja pareja) {
		for (Combatiente c : pareja) {
			c.recuperarEnergia(ENERGIA_POR_ASALTO);
		}
	}

	/*
	 * Turno de una pareja: cada integrante en pie, el de menor id primero, procesa sus
	 * efectos temporales (BONUS) y realiza una acción. Si el veneno lo deja fuera de
	 * combate, se avisa la eliminación. Si una pareja se queda sin integrantes en pie,
	 * el turno se corta.
	 * Lo usa: jugarAsalto.
	 */
	private void jugarTurno(Pareja propia, Pareja rival) {
		for (Combatiente c : propia) {
			if (terminado()) {
				return;
			}
			c.procesarEfectosTurno();
			if (!c.puedeActuar()) {
				notificador.publicar(new Eliminacion(ronda, asalto, c, propia));
			} else if (!terminado()) {
				actuar(c, propia, rival);
			}
		}
	}

	/*
	 * La acción de un combatiente. Elige el objetivo, le pide a su criterio (STRATEGY)
	 * una técnica respetando la regla del compañero y la usa; si no hay técnica, o si
	 * falla con TecnicaNoDisponibleException, hace un ataque básico. Las técnicas
	 * defensivas y de recuperación afectan al que las usa. Después avisa la acción y,
	 * si el objetivo cayó, su eliminación.
	 * Lo usa: jugarTurno.
	 */
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
				if (tecnica.getTipoTecnica() != TipoTecnica.OFENSIVA) {
					afectado = c;
				}
			} catch (TecnicaNoDisponibleException e) {
				c.atacar(objetivo);
			}
		} else {
			c.atacar(objetivo);
		}
		notificador.publicar(new AccionRealizada(ronda, asalto, c, accion, afectado));
		if (!objetivo.puedeActuar()) {
			notificador.publicar(new Eliminacion(ronda, asalto, objetivo, rival));
		}
	}

	/*
	 * Elige como objetivo al rival en pie con menos vida; en empate, al de menor id.
	 * Los recorre en orden de id y solo cambia si encuentra uno con MENOS vida ("<").
	 * Lo usan: actuar y CombateTest.
	 */
	public Combatiente elegirObjetivo(Pareja rival) {
		Combatiente elegido = null;
		for (Combatiente c : rival) {
			if (elegido == null || c.getVidaActual() < elegido.getVidaActual()) {
				elegido = c;
			}
		}
		return elegido;
	}

	/*
	 * Al llegar al límite de asaltos gana la pareja con más vida total; en empate,
	 * la de menor id (primera).
	 * Lo usan: jugar y CombateTest.
	 */
	public Pareja ganadorPorVida() {
		if (segunda.vidaTotal() > primera.vidaTotal()) {
			return segunda;
		}
		return primera;
	}

	/*
	 * Indica si alguna pareja se quedó sin integrantes en pie.
	 * Lo usan: jugar, jugarAsalto y jugarTurno.
	 */
	public boolean terminado() {
		return !primera.tieneIntegrantesEnPie() || !segunda.tieneIntegrantesEnPie();
	}

	/*
	 * Consultas del combate: la ganadora, las dos parejas, la ronda y cuántos asaltos
	 * se jugaron. getGanadora lo usa Cruce.getGanador; el resto queda para consultas.
	 */
	public Pareja getGanadora() { return ganadora; }
	public Pareja getPrimera() { return primera; }
	public Pareja getSegunda() { return segunda; }
	public int getRonda() { return ronda; }
	public int getAsaltosJugados() { return asalto; }
}
