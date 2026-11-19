package interesado;

import combatiente.Combatiente;

public class AccionRealizada extends Acontecimiento {
	private final int asalto;
	private final String ejecutor;
	private final String accion;      // nombre de la técnica o "ataque básico"
	private final String objetivo;
	private final int vidaObjetivo;   // vida del objetivo justo después de la acción

	public AccionRealizada(int ronda, int asalto, Combatiente ejecutor, String accion, Combatiente objetivo) {
		super(ronda);
		this.asalto = asalto;
		this.ejecutor = ejecutor.getNombre();
		this.accion = accion;
		this.objetivo = objetivo.getNombre();
		this.vidaObjetivo = objetivo.getVidaActual();
	}

	@Override
	public void notificarA(Interesado interesado) {
		interesado.accionRealizada(this);
	}

	@Override
	public String descripcion() {
		return "  Asalto " + asalto + ": " + ejecutor + " usa " + accion + " sobre " + objetivo
				+ " (queda con " + vidaObjetivo + " de vida)";
	}

	public int getAsalto() { return asalto; }
	public String getEjecutor() { return ejecutor; }
	public String getAccion() { return accion; }
	public String getObjetivo() { return objetivo; }
	public int getVidaObjetivo() { return vidaObjetivo; }
}