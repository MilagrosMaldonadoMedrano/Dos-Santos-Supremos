package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Sello extends Equipamiento{
	private int vidaExtra;
	
	public Sello(String nombre, int vidaExtra) {
		super(nombre);
		if (vidaExtra <= 0) throw new IllegalArgumentException("La vida extra debe ser positiva");
		this.vidaExtra = vidaExtra;
	}
	
	public Categoria getCategoria() { 
		return Categoria.SELLO; }
	
	public void aplicarEfecto(Combatiente c) { 
		c.modificarVidaMaxima(vidaExtra); }   // más vida máxima
	
	public void quitarEfecto(Combatiente c)  { 
		c.modificarVidaMaxima(-vidaExtra); }

	public int getVidaExtra() {
		return vidaExtra;
	}

	
	///agregar un toString que muestre el nombre del sello y la vida extra que da
	@Override
	public String toString() {
		return "Sello: " + super.getNombre() + " - Vida extra: " + vidaExtra;
	}
}
