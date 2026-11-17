package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Anillo extends Equipamiento{
	private int energiaExtra;
	
	public Anillo(String nombre, int energiaExtra) {
		super(nombre);
		if (energiaExtra <= 0) throw new IllegalArgumentException("La energía extra debe ser positiva");
		this.energiaExtra = energiaExtra;
	}
	public Categoria getCategoria() { 
		return Categoria.ANILLO; }
	
	public void aplicarEfecto(Combatiente c) { 
		c.modificarEnergiaMaxima(energiaExtra); }   // más energía máxima
	
	public void quitarEfecto(Combatiente c)  { 
		c.modificarEnergiaMaxima(-energiaExtra); }

	public int getEnergiaExtra() {
		return energiaExtra;
	}

	
	@Override
	public String toString() {
		return "Anillo: " + super.getNombre() + " - Energía extra: " + energiaExtra;
	}
}
