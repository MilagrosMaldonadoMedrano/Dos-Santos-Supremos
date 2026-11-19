package interesado;

import combatiente.Combatiente;
import torneo.Pareja;

public class Eliminacion extends Acontecimiento {
	private final int asalto;
	private final String caido;
	private final int idPareja;

	public Eliminacion(int ronda, int asalto, Combatiente caido, Pareja pareja) {
		super(ronda);
		this.asalto = asalto;
		this.caido = caido.getNombre();
		this.idPareja = pareja.getId();
	}

	@Override
	public void notificarA(Interesado interesado) {
		interesado.eliminacion(this);
	}

	@Override
	public String descripcion() {
		return "  Asalto " + asalto + ": " + caido + " (pareja " + idPareja + ") queda fuera de combate";
	}

	public int getAsalto() { return asalto; }
	public String getCaido() { return caido; }
	public int getIdPareja() { return idPareja; }
}