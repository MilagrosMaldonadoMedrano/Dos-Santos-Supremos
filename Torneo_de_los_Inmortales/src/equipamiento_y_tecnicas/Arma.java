package equipamiento_y_tecnicas;

import combatiente.Combatiente;

public class Arma extends Equipamiento{

	private int dañoExtra;
	
	public Arma(String nombre, int dañoExtra) {
		super(nombre);
		if (dañoExtra <= 0) throw new IllegalArgumentException("El daño extra debe ser positivo");
		this.dañoExtra = dañoExtra;
	}
	
	public int getDañoExtra() {
		return dañoExtra;
	}
	@Override
	public Categoria getCategoria() {
		return Categoria.ARMA;
	}

	//el arma modifica las capacidades del combatiente mientras las lleva
	@Override
	public void aplicarEfecto(Combatiente c) {
		c.modificarDanio(dañoExtra);
	}

	// Al quitarla se resta lo mismo que se sumo anteriormente, el combatiente vuelve a como estaba.
		@Override
		public void quitarEfecto(Combatiente c) {
			c.modificarDanio(-dañoExtra);
		}

	@Override
	public String toString() {
		return "Arma: " + super.getNombre() + " - Daño extra: " + dañoExtra;
	}

}
