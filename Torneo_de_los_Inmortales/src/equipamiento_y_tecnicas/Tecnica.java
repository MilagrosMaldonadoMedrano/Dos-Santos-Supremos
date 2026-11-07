package equipamiento_y_tecnicas;

import java.util.Objects;

import combatiente.Combatiente;
import efectos.Efecto;

public abstract class Tecnica {
	private String nombre;
    private int costoEnergia;
    private TipoTecnica tipo;
    protected Efecto efecto;
    
    public Tecnica(String nombre, int costoEnergia, TipoTecnica tipo, Efecto efecto) {
    	this.nombre = nombre;
    	this.costoEnergia = costoEnergia;
    	this.tipo = tipo;
    	this.efecto = efecto;
    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public int getCostoEnergia() {
    	return costoEnergia;
    }
    
    public TipoTecnica getTipoTecnica() {
    	return tipo;
    }
    
    public void realizar(Combatiente ejecutor, Combatiente objetivo) {

		if(ejecutor.getEnergiaActual() < getCostoEnergia())
			throw new IllegalStateException(ejecutor.getNombre() + " no tiene suficiente energia para usar la tecnica: " + this.nombre);

		ejecutor.gastarEnergia(getCostoEnergia());

		efecto.aplicarEfecto(tipo == TipoTecnica.OFENSIVA ? objetivo : ejecutor);
	}
    
    @Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tecnica other = (Tecnica) obj;
		return Objects.equals(nombre, other.nombre);
	}
}

