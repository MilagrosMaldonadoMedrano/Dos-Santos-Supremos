package equipamiento_y_tecnicas;

import java.util.Objects;

import combatiente.Combatiente;
import efectos.Efecto;
import excepciones.TecnicaNoDisponibleException;




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
    
<<<<<<< HEAD
    public void realizar(Combatiente ejecutor, Combatiente objetivo) {
    	// se decide primero a quien afecta y se valida todo ANTES De cobrar la energia
    	Combatiente afectado = (tipo == TipoTecnica.OFENSIVA) ? objetivo : ejecutor;
    	if (!afectado.puedeSerObjetivo())
    		throw new IllegalStateException(afectado.getNombre() + " no puede ser objetivo");
    	if (!ejecutor.tieneEnergiaPara(getCostoEnergia()))
    		throw new IllegalStateException(ejecutor.getNombre() + " no tiene suficiente energia para usar la tecnica: " + nombre);
    	ejecutor.gastarEnergia(getCostoEnergia());
    	efecto.aplicarEfecto(ejecutor, afectado);
    }
=======
	// Lanza TecnicaNoDisponibleException (verificada) si el objetivo no está en pie
	// o si al ejecutor no le alcanza la energía. Se valida todo ANTES de cobrar la energía.
	public void realizar(Combatiente ejecutor, Combatiente objetivo) throws TecnicaNoDisponibleException {
		Combatiente afectado = (tipo == TipoTecnica.OFENSIVA) ? objetivo : ejecutor;
		if (!afectado.puedeSerObjetivo())
			throw new TecnicaNoDisponibleException(afectado.getNombre() + " no puede ser objetivo");
		if (!ejecutor.tieneEnergiaPara(getCostoEnergia()))
			throw new TecnicaNoDisponibleException(ejecutor.getNombre() + " no tiene suficiente energia para usar la tecnica: " + nombre);
		ejecutor.gastarEnergia(getCostoEnergia());
		efecto.aplicarEfecto(ejecutor, afectado);
	}
>>>>>>> origin/main
    
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

