package interesado;

import torneo.Pareja;

/*
 * OBSERVER: acontecimiento de la proclamación de la pareja campeona (los Dos Santos
 * Supremos). Guarda la última ronda y el id y los integrantes de la campeona.
 * Lo crea: Torneo.iniciar, al terminar la última ronda.
 */
public class Proclamacion extends Acontecimiento {
	private final int idPareja;
	private final String integrantes;

	/*
	 * Copia los datos de la pareja campeona.
	 * Lo usa: Torneo.iniciar.
	 */
	public Proclamacion(int ronda, Pareja campeona) {
		super(ronda);
		this.idPareja = campeona.getId();
		this.integrantes = integrantes(campeona);
	}

	/*
	 * Avisa al interesado con su método proclamacion.
	 * Lo usa: Notificador.publicar.
	 */
	@Override
	public void notificarA(Interesado interesado) {
		interesado.proclamacion(this);
	}

	/*
	 * Texto del acontecimiento, por ejemplo "Kael y Darian (pareja 2) son proclamados...".
	 * Lo usa: HeraldoImperial.
	 */
	@Override
	public String descripcion() {
		return integrantes + " (pareja " + idPareja + ") son proclamados los Dos Santos Supremos";
	}

	/*
	 * Consultas de los datos guardados.
	 * Las usa: AcontecimientoTest.
	 */
	public int getIdPareja() { return idPareja; }
	public String getIntegrantes() { return integrantes; }
}
