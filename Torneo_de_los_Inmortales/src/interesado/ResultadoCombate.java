package interesado;

import torneo.Pareja;

// §7: ronda, ganadora y vida total de cada pareja al terminar.
public class ResultadoCombate extends Acontecimiento {
	private final int idGanadora;
	private final int idParejaA;
	private final String integrantesA;
	private final int vidaA;
	private final int idParejaB;
	private final String integrantesB;
	private final int vidaB;

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

	@Override
	public void notificarA(Interesado interesado) {
		interesado.resultadoCombate(this);
	}

	@Override
	public String descripcion() {
		return "Ronda " + getRonda() + ": gana la pareja " + idGanadora + " | vida total: pareja " + idParejaA
				+ " = " + vidaA + ", pareja " + idParejaB + " = " + vidaB;
	}

	public int getIdGanadora() { return idGanadora; }
	public int getIdParejaA() { return idParejaA; }
	public String getIntegrantesA() { return integrantesA; }
	public int getVidaA() { return vidaA; }
	public int getIdParejaB() { return idParejaB; }
	public String getIntegrantesB() { return integrantesB; }
	public int getVidaB() { return vidaB; }
}