package interesado;

import torneo.Pareja;

/*
 * OBSERVER: acontecimiento del comienzo de un combate. Guarda la ronda y el id y los
 * integrantes de las dos parejas.
 * Lo crea: Combate.jugar.
 */
public class InicioCombate extends Acontecimiento {
	private final int idParejaA;
	private final String integrantesA;
	private final int idParejaB;
	private final String integrantesB;

	/*
	 * Copia los datos de las dos parejas en el momento en que empieza el combate.
	 * Lo usa: Combate.jugar.
	 */
	public InicioCombate(int ronda, Pareja a, Pareja b) {
		super(ronda);
		this.idParejaA = a.getId();
		this.integrantesA = integrantes(a);
		this.idParejaB = b.getId();
		this.integrantesB = integrantes(b);
	}

	/*
	 * Avisa al interesado con su método inicioCombate.
	 * Lo usa: Notificador.publicar.
	 */
	@Override
	public void notificarA(Interesado interesado) {
		interesado.inicioCombate(this);
	}

	/*
	 * Texto del acontecimiento, por ejemplo "Ronda 1: comienza el combate entre...".
	 * Lo usa: HeraldoImperial.
	 */
	@Override
	public String descripcion() {
		return "Ronda " + getRonda() + ": comienza el combate entre la pareja " + idParejaA + " (" + integrantesA
				+ ") y la pareja " + idParejaB + " (" + integrantesB + ")";
	}

	/*
	 * Consultas de los datos guardados.
	 * Las usa: AcontecimientoTest.
	 */
	public int getIdParejaA() { return idParejaA; }
	public String getIntegrantesA() { return integrantesA; }
	public int getIdParejaB() { return idParejaB; }
	public String getIntegrantesB() { return integrantesB; }
}
