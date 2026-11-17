package efectos;

import combatiente.Combatiente;

public class Curacion implements Efecto{
	private int cantidad;
	
	public Curacion(int cantidad) {
		this.cantidad = cantidad;
	}
	
	///como modifique implemento y esta clase lo implementa modifico la firma de
	/// aplicarEfecto
	@Override
	public void aplicarEfecto(Combatiente ejecutor, Combatiente afectado) {
		afectado.curar(cantidad);
	}

}
