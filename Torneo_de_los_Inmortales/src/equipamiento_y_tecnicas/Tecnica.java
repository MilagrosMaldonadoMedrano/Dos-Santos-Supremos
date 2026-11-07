package equipamiento_y_tecnicas;

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
    
    public abstract void realizar(Combatiente ejecutor, Combatiente objetivo);
}

