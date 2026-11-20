package interesado;

import combatiente.Combatiente;

/*
 * OBSERVER: acontecimiento de una acción en el combate (ataque básico o técnica).
 * Guarda el asalto, quién actuó, qué hizo, sobre quién y con cuánta vida quedó el objetivo.
 * Lo crea: Combate.actuar.
 */
public class AccionRealizada extends Acontecimiento {
	private final int asalto;
	private final String ejecutor;
	private final String accion;      // nombre de la técnica o "ataque básico"
	private final String objetivo;
	private final int vidaObjetivo;   // vida del objetivo justo después de la acción

	/*
	 * Copia los nombres y la vida del objetivo en el momento de la acción.
	 * Lo usa: Combate.actuar.
	 */
	public AccionRealizada(int ronda, int asalto, Combatiente ejecutor, String accion, Combatiente objetivo) {
		super(ronda);
		this.asalto = asalto;
		this.ejecutor = ejecutor.getNombre();
		this.accion = accion;
		this.objetivo = objetivo.getNombre();
		this.vidaObjetivo = objetivo.getVidaActual();
	}

	/*
	 * Avisa al interesado con su método accionRealizada.
	 * Lo usa: Notificador.publicar.
	 */
	@Override
	public void notificarA(Interesado interesado) {
		interesado.accionRealizada(this);
	}

	/*
	 * Texto del acontecimiento, por ejemplo "Asalto 2: Aurelio usa ... sobre ...".
	 * Lo usa: HeraldoImperial.
	 */
	@Override
	public String descripcion() {
		return "  Asalto " + asalto + ": " + ejecutor + " usa " + accion + " sobre " + objetivo
				+ " (queda con " + vidaObjetivo + " de vida)";
	}

	/*
	 * Consultas de los datos guardados.
	 * Las usa: AcontecimientoTest.
	 */
	public int getAsalto() { return asalto; }
	public String getEjecutor() { return ejecutor; }
	public String getAccion() { return accion; }
	public String getObjetivo() { return objetivo; }
	public int getVidaObjetivo() { return vidaObjetivo; }
}
