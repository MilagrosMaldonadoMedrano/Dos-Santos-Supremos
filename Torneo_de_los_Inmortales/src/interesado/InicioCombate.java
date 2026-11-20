package interesado;

import torneo.Pareja;

public class InicioCombate extends Acontecimiento {
	private final int idParejaA;
	private final String integrantesA;
	private final int idParejaB;
	private final String integrantesB;

	public InicioCombate(int ronda, Pareja a, Pareja b) {
		super(ronda);
		this.idParejaA = a.getId();
		this.integrantesA = integrantes(a);
		this.idParejaB = b.getId();
		this.integrantesB = integrantes(b);
	}

	@Override
	public void notificarA(Interesado interesado) {
		interesado.inicioCombate(this);
	}

	@Override
	public String descripcion() {
		return "Ronda " + getRonda() + ": comienza el combate entre la pareja " + idParejaA + " (" + integrantesA
				+ ") y la pareja " + idParejaB + " (" + integrantesB + ")";
	}

	public int getIdParejaA() { return idParejaA; }
	public String getIntegrantesA() { return integrantesA; }
	public int getIdParejaB() { return idParejaB; }
	public String getIntegrantesB() { return integrantesB; }
}