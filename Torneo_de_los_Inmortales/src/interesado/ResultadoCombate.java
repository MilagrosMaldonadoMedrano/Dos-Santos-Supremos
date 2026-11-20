package interesado;

import torneo.Pareja;

/*
 * OBSERVER: acontecimiento del final de un combate. Guarda la ronda, la pareja ganadora
 * y el id, los integrantes y la vida total de cada pareja al terminar.
 * Lo crea: Combate.jugar, antes de finalizar el combate.
 */
public class ResultadoCombate extends Acontecimiento {
	private final int idGanadora;
	private final int idParejaA;
	private final String integrantesA;
	private final int vidaA;
	private final int idParejaB;
	private final String integrantesB;
	private final int vidaB;

	/*
	 * Copia los datos de las dos parejas en el momento en que termina el combate.
	 * Lo usa: Combate.jugar.
	 */
	public ResultadoCombate(int ronda, Pareja ganadora, Pareja a, Pareja b) {
		super(ronda);
		this.idGanadora = ganadora.getId();
		this.idParejaA = a.getId();
		this.integrantesA = integrantes(a);
		this.vidaA = a.vidaTotal();
		this.idParejaB = b.getId();
		this.integrantesB = integrantes(b);
		this.vidaB = b.vidaTotal();
	}

	/*
	 * Avisa al interesado con su método resultadoCombate.
	 * Lo usa: Notificador.publicar.
	 */
	@Override
	public void notificarA(Interesado interesado) {
		interesado.resultadoCombate(this);
	}

	/*
	 * Texto del acontecimiento, por ejemplo "Ronda 1: gana la pareja 2 | vida total...".
	 * Lo usa: HeraldoImperial.
	 */
	@Override
	public String descripcion() {
		return "Ronda " + getRonda() + ": gana la pareja " + idGanadora + " | vida total: pareja " + idParejaA
				+ " = " + vidaA + ", pareja " + idParejaB + " = " + vidaB;
	}

	/*
	 * Consultas de los datos guardados.
	 * Las usan: CronistaDeLaSecta (para armar la tabla de posiciones) y las pruebas.
	 */
	public int getIdGanadora() { return idGanadora; }
	public int getIdParejaA() { return idParejaA; }
	public String getIntegrantesA() { return integrantesA; }
	public int getVidaA() { return vidaA; }
	public int getIdParejaB() { return idParejaB; }
	public String getIntegrantesB() { return integrantesB; }
	public int getVidaB() { return vidaB; }
}
