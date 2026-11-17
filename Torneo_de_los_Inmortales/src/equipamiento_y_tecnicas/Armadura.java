package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Armadura extends Equipamiento{
	private int armaduraExtra;
	
	public Armadura(String nombre, int armaduraExtra) {
		super(nombre);
		if (armaduraExtra <= 0) throw new IllegalArgumentException("La armadura extra debe ser positiva");
		this.armaduraExtra = armaduraExtra;
	}
	
	public Categoria getCategoria() {
		return Categoria.ARMADURA; }
	
	public void aplicarEfecto(Combatiente c) { 
		c.modificarReduccionDanio(armaduraExtra); }  // menos danioo recibido
	
	public void quitarEfecto(Combatiente c)  { 
		c.modificarReduccionDanio(-armaduraExtra); }
	
	
	public int getArmaduraExtra() {
		return armaduraExtra;
	}
	
	@Override
	public String toString() {
		return "Armadura: " + super.getNombre() + " - Armadura extra: " + armaduraExtra;
	}
}
