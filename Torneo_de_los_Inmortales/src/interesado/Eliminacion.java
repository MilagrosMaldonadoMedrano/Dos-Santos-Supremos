package interesado;

import combatiente.Combatiente;
import torneo.Pareja;

/*
 * OBSERVER: acontecimiento de un combatiente que queda fuera de combate (por un golpe
 * o por el veneno). Guarda el asalto, el nombre del caído y el id de su pareja.
 * Lo crea: Combate (en actuar y en jugarTurno).
 */
public class Eliminacion extends Acontecimiento {
	private final int asalto;
	private final String caido;
	private final int idPareja;

	/*
	 * Copia los datos del caído en el momento en que cae.
	 * Lo usa: Combate.
	 */
	public Eliminacion(int ronda, int asalto, Combatiente caido, Pareja pareja) {
		super(ronda);
		this.asalto = asalto;
		this.caido = caido.getNombre();
		this.idPareja = pareja.getId();
	}

	/*
	 * Avisa al interesado con su método eliminacion.
	 * Lo usa: Notificador.publicar.
	 */
	@Override
	public void notificarA(Interesado interesado) {
		interesado.eliminacion(this);
	}

	/*
	 * Texto del acontecimiento, por ejemplo "Asalto 4: Nébula (pareja 1) queda fuera...".
	 * Lo usa: HeraldoImperial.
	 */
	@Override
	public String descripcion() {
		return "  Asalto " + asalto + ": " + caido + " (pareja " + idPareja + ") queda fuera de combate";
	}

	/*
	 * Consultas de los datos guardados.
	 * Las usa: AcontecimientoTest.
	 */
	public int getAsalto() { return asalto; }
	public String getCaido() { return caido; }
	public int getIdPareja() { return idPareja; }
}
