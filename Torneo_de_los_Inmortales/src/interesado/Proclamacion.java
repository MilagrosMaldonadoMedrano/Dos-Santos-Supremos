package interesado;

import torneo.Pareja;

public class Proclamacion extends Acontecimiento {
	private final int idPareja;
	private final String integrantes;

	public Proclamacion(int ronda, Pareja campeona) {
		super(ronda);
		this.idPareja = campeona.getId();
		this.integrantes = integrantes(campeona);
	}

	@Override
	public void notificarA(Interesado interesado) {
		interesado.proclamacion(this);
	}

	@Override
	public String descripcion() {
		return integrantes + " (pareja " + idPareja + ") son proclamados los Dos Santos Supremos";
	}

	public int getIdPareja() { return idPareja; }
	public String getIntegrantes() { return integrantes; }
}